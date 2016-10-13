package app

import basic.FileInfo
import grails.converters.JSON

/**
 * Created by ceny on 2016/10/9.
 */
class FileController {

    def uploadFile(){
        def file = request.getFile('file')
        def name = file.originalFilename
        if(params["newName"]){
            name = params["newName"]+name.substring(name.lastIndexOf("."))
        }
        File folder =new File("src/main/webapp/files");
        if(!folder.exists()){// && !folder.isDirectory()
            println "the folder do not exists"
            folder.mkdir()
        }
        //TODO check name

        def fis = new ByteArrayInputStream(file.getBytes())
        OutputStream out = new FileOutputStream(new File("src/main/webapp/files/"+name))
        out<<fis
        out.close()
        fis.close()
        def fileInfo = new FileInfo()
        fileInfo.name = name
        fileInfo.size = file.getBytes().length
        fileInfo.contentType = file.contentType
        fileInfo.comment = params["comment"]
        fileInfo.date = new Date()
        fileInfo.save(flush:true)
        render fileInfo as JSON
    }

    def listFile(){
        def kw = params["keywords"]
        def tmp = FileInfo.findAll()
        if(kw){
            def ans = []
            tmp.each {
                String str = it["name"]+it["comment"]
                if(str.contains(kw)) {
                    ans.add(it)
                }
            }
            render ans.reverse() as JSON
        }
        else{
            render tmp.reverse() as JSON
        }

    }

    def deleteFile(int id){
        FileInfo fileInfo = FileInfo.get(id)
        if(fileInfo){
            File file = new File("src/main/webapp/files/"+fileInfo["name"])
            if(file.exists()){
                file.delete()
            }
            fileInfo.delete(flush:true)
            def ans = ["status":1]
            render ans as JSON
        }
        else{
            def ans = ["status":0]
            render ans as JSON
        }
    }



}


//        new CustomersImportLog(
//                fileId:id,
//                fileName:attachment.name,
//                failedFileId:failedId,
//                successedNum:successedNum,
//                failedNum:failedNum,
//                importTime:new Date()
//        ).save()
