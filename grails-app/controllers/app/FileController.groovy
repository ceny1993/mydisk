package app

import basic.Attachment
import grails.converters.JSON

/**
 * Created by ceny on 2016/10/9.
 */
class FileController {
    def upload(){
        def file = request.getFile('file')
        def newName = params["newName"]
        def comment = params["comment"]
        def oldName = file.originalFilename
        if(newName){
            newName = newName + oldName.substring(oldName.lastIndexOf("."))
            println newName
        }
        println file.originalFilename
        println file.contentType
        println file.getBytes().length
        def att = new Attachment()
        att.newName = newName
        att.oldName = oldName
        att.contentType = file.contentType
        att.content = file.getBytes()
        att.size = att.content.length
        att.date = new Date()
        att.comment = comment

        att.save(flush:true)
        render "ok"
    }

    def download(){
        Attachment att = Attachment.get(params["id"])
        if(att == null){
            render status:404, text:"Document not found"
        }else{
            response.setContentType(att.contentType)
            String filename =""
            if(att.newName){
                filename = new String((att.newName).getBytes("UTF-8"),"iso8859-1")
            }
            else{
                filename = new String((att.oldName).getBytes("UTF-8"),"iso8859-1")
            }
            //String filename = new String((att.oldName).getBytes("UTF-8"),"iso8859-1")
            response.setHeader("Content-Disposition", "Attachment;Filename=\"${filename}\"")
            def fis = new ByteArrayInputStream(att.content)
            def os = response.getOutputStream()
            byte[] buffer = new byte[4096]
            int len;
            while((len = fis.read(buffer))>0){
                os.write(buffer,0,len);
            }
            os.flush()
            os.close()
            fis.close()
        }
    }

    def list(){
        def ans = Attachment.executeQuery("SELECT id,contentType,newName,oldName,size,date,comment FROM Attachment ORDER BY id DESC")
        render ans as JSON
    }

    def delete(){
        Attachment att = Attachment.get(params["id"])
        if(att == null){
            render status:404, text:"Document not found"
        }else{
            att.delete(flush:true)
            render "done"
        }
    }

    def search(){
        def kw = params["keywords"]
        def tmp = Attachment.executeQuery("SELECT id,contentType,newName,oldName,size,date,comment FROM Attachment ORDER BY id DESC")
        def ans = []
        tmp.each{
            String str = it[2]+it[3]+it[6]
            if(str.contains(kw)){
                ans.add(it)
            }
        }
        render ans as JSON
    }
}
