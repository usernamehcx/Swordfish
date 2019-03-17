package com.nju.datatypes;

import com.google.gson.Gson;

public class T_KJ_ZZQKM {

    private String JRXKZH;
    private String YHJGDM;
    private String NBJGH;
    private String YHJGMC;
    private String KMBH;
    private String KMMC;
    private int KMJC;
    private int KMLX;
    private float QCJFYE;
    private float QCDFYE;
    private float JFFSE;
    private float DFFSE;
    private float QMJFYE;
    private float QMDFYE;
    private String BZ;
    private String KJRQ;
    private int BSZQ;

    private boolean notnull;

    public T_KJ_ZZQKM(){ }

    public static T_KJ_ZZQKM fromString(String json_String){
        Gson gson = new Gson();
        T_KJ_ZZQKM row = gson.fromJson(json_String, T_KJ_ZZQKM.class);
        row.notnull = (row.JRXKZH != null) && (row.YHJGDM != null) && (row.NBJGH != null) && (row.YHJGMC != null) && (row.KMBH != null)
                        && (row.KMJC != -1) && (row.KMLX != -1) && (row.QCDFYE != -1) && (row.QCJFYE != -1) && (row.JFFSE != -1)  && (row.DFFSE != -1)
                        && (row.QMJFYE != -1) && (row.QMDFYE != -1) && (row.BZ != null) && (row.KJRQ != null) && (row.BSZQ != -1);
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


    public int getKMJC() {
        return KMJC;
    }
    public void setKMJC(int KMJC) {
        this.KMJC = KMJC;
    }

    public int getKMLX() {
        return KMLX;
    }
    public void setKMLX(int KMLX) {
        this.KMLX = KMLX;
    }

    public float getQCJFYE() {
        return QCJFYE;
    }
    public void setQCJFYE(float QCJFYE) {
        this.QCJFYE = QCJFYE;
    }

    public float getQCDFYE() {
        return QCDFYE;
    }
    public void setQCDFYE(float QCDFYE) {
        this.QCDFYE = QCDFYE;
    }

    public float getJFFSE() {
        return JFFSE;
    }
    public void setJFFSE(float JFFSE) {
        this.JFFSE = JFFSE;
    }

    public float getDFFSE() {
        return DFFSE;
    }
    public void setDFFSE(float DFFSE) {
        this.DFFSE = DFFSE;
    }

    public float getQMJFYE() {
        return QMJFYE;
    }
    public void setQMJFYE(float QMJFYE) {
        this.QMJFYE = QMJFYE;
    }

    public float getQMDFYE() {
        return QMDFYE;
    }
    public void setQMDFYE(float QMDFYE) {
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

    public int getBSZQ() {
        return BSZQ;
    }
    public void setBSZQ(int BSZQ) {
        this.BSZQ = BSZQ;
    }


    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
