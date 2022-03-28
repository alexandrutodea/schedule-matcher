package com.endava.tmd.soj.schedulematcher.service;

import java.util.Random;

/**
 * The {@code GroupCodeGenerator} class generates a random code of given length that is going to be used as the
 * identifier of a {@code ScheduleGroup} in the {@code ScheduleGroupManager} class
 */
public class GroupCodeGenerator {

    private GroupCodeGenerator() {
        //it makes no sense to instantiate this class
    }

    /**
     * @param length the length of the group code
     * @return the group code
     * @throws IllegalArgumentException if the length is smaller than 2
     */
    public static String getGroupCode(int length) {
        if (length < 2) {
            throw new IllegalArgumentException("Group code length cannot be smaller than 2");
        }

        var groupCode = new StringBuilder();
        var random = new Random();
        int max = 122;
        int min = 97;

        for (int i = 0; i < length; i++) {
            groupCode.append(Character.toString( min + random.nextInt(max - min + 1)));
        }

        return groupCode.toString();
    }

}
