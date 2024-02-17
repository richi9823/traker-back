package com.ricardo.traker.util;

import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;

public class CompareDate {

    public static NotificationEntity minNotificationEntity(NotificationEntity a, NotificationEntity b) {
        return a.getCreatedDate().compareTo(b.getCreatedDate()) < 0 ? a : b;
    }

    public static NotificationEntity maxNotificationEntity(NotificationEntity a, NotificationEntity b) {
        return a.getCreatedDate().compareTo(b.getCreatedDate()) > 0 ? a : b;
    }

    public static PositionEntity maxPosition(PositionEntity a, PositionEntity b) {
        return a.getTime().compareTo(b.getTime()) > 0 ? a : b;
    }
}
