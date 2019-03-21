package com.nju.datatypes;

import com.google.gson.Gson;

public class T_KJ_ZZQKM {

    private String JRXKZH;
    private String YHJGDM;
    private String NBJGH;
    private String YHJGMC;
    private String KMBH;
    private String KMMC;
    private String KMJC;
    private String KMLX;
    private String QCJFYE;
    private String QCDFYE;
    private String JFFSE;
    private String DFFSE;
    private String QMJFYE;
    private String QMDFYE;
    private String BZ;
    private String KJRQ;
    private String BSZQ;

    private boolean notnull;

    public T_KJ_ZZQKM(){ }

    public static T_KJ_ZZQKM fromString(String json_String){
        Gson gson = new Gson();
        T_KJ_ZZQKM row = gson.fromJson(json_String, T_KJ_ZZQKM.class);
        row.notnull = (row.JRXKZH != null) && (row.YHJGDM != null) && (row.NBJGH != null) && (row.YHJGMC != null) && (row.KMBH != null)
                        && (row.KMJC != null) && (row.KMLX != null) && (row.QCDFYE != null) && (row.QCJFYE != null) && (row.JFFSE != null)  && (row.DFFSE != null)
                        && (row.QMJFYE != null) && (row.QMDFYE != null) && (row.BZ != null) && (row.KJRQ != null) && (row.BSZQ != null);
        return row;
    }

    public void setNotnull(boolean notnull){
        this.notnull = notnull;
    }

    public boolean isNotnull(){
        return notnull;
    }

    public String getJRXKZH() {
        return JRXKZH;
    }
    public void setJRXKZH(String JRXKZH) {
        this.JRXKZH = JRXKZH;
    }

    public String getYHJGDM() {
        return YHJGDM;
    }
    public void setYHJGDM(String YHJGDM) {
        this.YHJGDM = YHJGDM;
    }

    public String getNBJGH() {
        return NBJGH;
    }
    public void setNBJGH(String NBJGH) {
        this.NBJGH = NBJGH;
    }

    public String getYHJGMC() {
        return YHJGMC;
    }
    public void setYHJGMC(String YHJGMC) {
        this.YHJGMC = YHJGMC;
    }

    public String getKMBH() {
        return KMBH;
    }
    public void setKMBH(String KMBH) {
        this.KMBH = KMBH;
    }

    public String getKMMC() {
        return KMMC;
    }
    public void setKMMC(String KMMC) {
        this.KMMC = KMMC;
    }


    public String getKMJC() {
        return KMJC;
    }
    public void setKMJC(String KMJC) {
        this.KMJC = KMJC;
    }

    public String getKMLX() {
        return KMLX;
    }
    public void setKMLX(String KMLX) {
        this.KMLX = KMLX;
    }

    public String getQCJFYE() {
        return QCJFYE;
    }
    public void setQCJFYE(String QCJFYE) {
        this.QCJFYE = QCJFYE;
    }

    public String getQCDFYE() {
        return QCDFYE;
    }
    public void setQCDFYE(String QCDFYE) {
        this.QCDFYE = QCDFYE;
    }

    public String getJFFSE() {
        return JFFSE;
    }
    public void setJFFSE(String JFFSE) {
        this.JFFSE = JFFSE;
    }

    public String getDFFSE() {
        return DFFSE;
    }
    public void setDFFSE(String DFFSE) {
        this.DFFSE = DFFSE;
    }

    public String getQMJFYE() {
        return QMJFYE;
    }
    public void setQMJFYE(String QMJFYE) {
        this.QMJFYE = QMJFYE;
    }

    public String getQMDFYE() {
        return QMDFYE;
    }
    public void setQMDFYE(String QMDFYE) {
        this.QMDFYE = QMDFYE;
    }

    public String getBZ() {
        return BZ;
    }
    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getKJRQ() {
        return KJRQ;
    }
    public void setKJRQ(String KJRQ) {
        this.KJRQ = KJRQ;
    }

    public String getBSZQ() {
        return BSZQ;
    }
    public void setBSZQ(String BSZQ) {
        this.BSZQ = BSZQ;
    }


    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
