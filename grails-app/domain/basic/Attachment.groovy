package basic

/**
 * Created by ceny on 2016/10/9.
 */
class Attachment {
    String newName
    String oldName
    Integer size
    String contentType
    byte[] content
    String comment
    Date date

    static constraints = {
        contentType (size: 0..256)
        content (nullable:true, maxSize:1073741824)//1G
//		name(shared:"name")
    }

    /*
    * static mapping = {
filePayload (type:’longblob’)
}
    *
    * */
}
