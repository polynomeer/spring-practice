package com.august.refactoring.catalog;

public class GuardClauses {

    public int getPoints() {
        if (isVip()) return vipPoint();
        if (isPlat()) return platPoint();
        return normalPoint();
    }

    private int normalPoint() {
        return 0;
    }

    private int platPoint() {
        return 1;
    }

    private int vipPoint() {
        return 2;
    }

    private boolean isPlat() {
        return false;
    }

    private boolean isVip() {
        return false;
    }
}
