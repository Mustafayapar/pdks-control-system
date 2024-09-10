package com.onbiron.onbironpdks.enums;

public enum ParentIdType {
	IZIN_TURLERI(0, "İzin Türleri"),
	RAPOR_TURLERI(0, "Rapor Türleri"),
	GIRIS_CIKIS_TURLERI(0, "Giriş Çıkış Türleri"),
	KULLANICI_ROLLERI(0, "Kullanıcı Rolleri"),
	YILLIK_IZIN(1, "Yıllık İzin"),
    MAZERET_IZNI(1, "Mazeret İzni"),
    DOGUM_IZNI(1, "Doğum İzni"),
    SAGLIK_RAPORU(2, "Sağlık Raporu"),
    GIRIS(3, "Giriş"),
    CIKIS(3, "Çıkış"),
    ADMIN(4, "Admin"),
    IK(4, "İK"),
    STANDART_KULLANICI(4, "Standart Kullanıcı");
	 

    private final int parentId;
    private final String name;

    ParentIdType(int parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    // Optional: You can create a method to find enum by parentId or description
    public static ParentIdType fromParentId(long parentId) {
        for (ParentIdType type : ParentIdType.values()) {
            if (type.getParentId() == parentId) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid parentId: " + parentId);
    }
}
