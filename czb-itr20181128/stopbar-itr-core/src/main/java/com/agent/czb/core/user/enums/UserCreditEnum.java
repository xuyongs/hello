package com.agent.czb.core.user.enums;

/**
 * @author linkai
 */
public interface UserCreditEnum {
    enum CreditType {
        A("注册", 0), B("签到", 5), C("完善资料", 20), D("其他", 10);

        private String desc;
        private int credit;
        CreditType(String desc, int credit) {
            this.desc = desc;
            this.credit = credit;
        }
        public int getCredit() {
            return credit;
        }
        public String toString() {
            return desc;
        }
    }

    enum UserPerType {
        A("已注册"), B("已完善");

        private String desc;

        UserPerType(String desc) {
            this.desc = desc;
        }
        public String toString() {
            return desc;
        }
    }
}
