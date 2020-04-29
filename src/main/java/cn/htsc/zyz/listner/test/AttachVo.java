package cn.htsc.zyz.listner.test;

import lombok.ToString;

import java.util.Objects;

@ToString
public class AttachVo {

    private String docTitle;

    private String docId;//文件的主键

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public AttachVo(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachVo attachVo = (AttachVo) o;
        return Objects.equals(docTitle, attachVo.docTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docTitle);
    }
}
