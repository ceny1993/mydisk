package mydisk

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/file/upload"(controller:"file",action:"uploadFile",method:"POST")
        "/file"(controller:"file",action:"listFile",method: "GET")
        "/file/$id"(controller:"file",action:"deleteFile",method: "POST")
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
