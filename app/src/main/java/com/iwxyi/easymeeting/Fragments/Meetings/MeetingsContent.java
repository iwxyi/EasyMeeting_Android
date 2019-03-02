package com.iwxyi.easymeeting.Fragments.Meetings;

import com.iwxyi.easymeeting.Fragments.Leases.LeaseContent;
import com.iwxyi.easymeeting.Utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 * 本来是想继承 LeasesContent，但是发现不行诶……除非把 LeaseContent 的 static 去掉
 */
public class MeetingsContent /*extends LeaseContent */{

    public static final List<MeetingItem> ITEMS = new ArrayList<MeetingItem>();
    public static final Map<Integer, MeetingItem> ITEM_MAP = new HashMap<Integer, MeetingItem>();

    public static void addItemsFromString(String str) {
        ITEMS.clear();
        ITEM_MAP.clear();
        ArrayList<String> list = StringUtil.getXmls(str, "lease");
        for (String s : list) {
            MeetingItem item = new MeetingItem(s);
            addItem(item);
        }
    }

    private static void addItem(MeetingItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.lease_id, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class MeetingItem {
        public int lease_id;
        public int room_id;
        public int admin_id;
        public int user_id;
        public String room_name;
        public String admin_name;
        public int start_time;
        public int finish_time;
        public String theme;
        public String usage;
        public String message;
        public boolean sweep;
        public boolean entertain;
        public boolean remote;
        public String circumstance;
        public int admin_score;
        public int user_score;
        public int credit_change;
        public int create_time;
        public int update_time;

        public MeetingItem(int lease_id, int room_id, int admin_id, int user_id, String room_name, String admin_name, int start_time, int finish_time, String theme, String usage, String message, boolean sweep, boolean entertain, boolean remote, String circumstance, int admin_score, int user_score, int credit_change, int create_time, int update_time) {
            this.lease_id = lease_id;
            this.room_id = room_id;
            this.admin_id = admin_id;
            this.user_id = user_id;
            this.room_name = room_name;
            this.admin_name = admin_name;
            this.start_time = start_time;
            this.finish_time = finish_time;
            this.theme = theme;
            this.usage = usage;
            this.message = message;
            this.sweep = sweep;
            this.entertain = entertain;
            this.remote = remote;
            this.circumstance = circumstance;
            this.admin_score = admin_score;
            this.user_score = user_score;
            this.credit_change = credit_change;
            this.create_time = create_time;
            this.update_time = update_time;
        }

        public MeetingItem(String str) {
            fromString(str);
        }

        public MeetingItem(String id, String content, String details) {
            this.lease_id = Integer.parseInt(id);
            this.theme = content;
            this.usage = details;
        }

        public void fromString(String str) {
            this.lease_id = StringUtil.getXmlInt(str, "lease_id");
            this.room_id = StringUtil.getXmlInt(str, "room_id");
            this.admin_id = StringUtil.getXmlInt(str, "admin_id");
            this.user_id = StringUtil.getXmlInt(str, "user_id");
            this.room_name = StringUtil.getXml(str, "room_name");
            this.admin_name = StringUtil.getXml(str, "admin_name");
            this.start_time = StringUtil.getXmlInt(str, "start_time");
            this.finish_time = StringUtil.getXmlInt(str, "finish_time");
            this.theme = StringUtil.getXml(str, "theme");
            this.usage = StringUtil.getXml(str, "usage");
            this.message = StringUtil.getXml(str, "message");
            this.sweep = StringUtil.getXmlBoolean(str, "sweep");
            this.entertain = StringUtil.getXmlBoolean(str, "entertain");
            this.remote = StringUtil.getXmlBoolean(str, "remote");
            this.circumstance = StringUtil.getXml(str, "circumstance");
            this.admin_score = StringUtil.getXmlInt(str, "admin_score");
            this.user_score = StringUtil.getXmlInt(str, "user_score");
            this.credit_change = StringUtil.getXmlInt(str, "credit_change");
            this.create_time = StringUtil.getXmlInt(str, "create_time");
            this.update_time = StringUtil.getXmlInt(str, "update_time");
        }

        public String toString() {
            String str = "<lease_id>" + this.lease_id + "</lease_id>"
                    + "<room_id>" + this.room_id + "</room_id>"
                    + "<admin_id>" + this.admin_id + "</admin_id>"
                    + "<user_id>" + this.user_id + "</user_id>"
                    + "<room_name>" + this.room_name + "</room_name>"
                    + "<admin_name>" + this.admin_name + "</admin_name>"
                    + "<start_time>" + this.start_time + "</start_time>"
                    + "<finish_time>" + this.finish_time + "</finish_time>"
                    + "<theme>" + this.theme + "</theme>"
                    + "<usage>" + this.usage + "</usage>"
                    + "<message>" + this.message + "</message>"
                    + "<sweep>" + this.sweep + "</sweep>"
                    + "<entertain>" + this.entertain + "</entertain>"
                    + "<remote>" + this.remote + "</remote>"
                    + "<admin_score>" + this.admin_score + "</admin_score>"
                    + "<user_score>" + this.user_score + "</user_score>"
                    + "<credit_change>" + this.credit_change + "</credit_change>"
                    + "<create_time>" + this.create_time + "</create_time>"
                    + "<update_time>" + this.update_time + "</update_time>";
            str = "<lease>" + str + "</lease>";
            return str;
        }
    }
}
