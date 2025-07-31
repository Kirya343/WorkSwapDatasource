package org.workswap.datasource.admin.model.enums;

public enum TaskType {
    DEVELOPMENT_BACKEND("Разработка(FE)"),
    DEVELOPMENT_FRONTEND("Разработка(BE)"),
    CONTENT_UPDATE("Дополнение контента"),
    MODERATION("Модерация"),
    DESIGN("Дизайн"),
    TESTING("Тестирование"),
    MAINTENANCE("Обслуживание"),
    SUPPORT("Поддержка");

    private final String displayName;

    TaskType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
