package basic

import com.sun.istack.internal.Nullable

/**
 * Created by ceny on 2016/10/12.
 */
class FileInfo {
    String name
    Integer size
    String contentType
    String comment
    Date date

    static constraints = {
        contentType (size: 0..1024)
        name(nullable:true,unique:true,maxSize:1024)
//        content (nullable:true, maxSize:1073741824)//1G
//		name(shared:"name")
    }
}
