package com.threehmis.xcjc.api;

import java.util.List;

/**
 * Created by llz on 2018/2/2.
 */

public class OrginzatioinUserBean {

    private List<RowsBean> rows;

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : e1aa5bd6dcc44ff085a20799df261bb5
         * orgnizationId : 10b00526bd0a467ba00a7dbedd1ef4e6
         * userAccount : 3hlkq
         * userName : lkq
         * customerId : 000000012
         */

        private String id;
        private String orgnizationId;
        private String userAccount;
        private String userName;
        private String customerId;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private boolean isSelect;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgnizationId() {
            return orgnizationId;
        }

        public void setOrgnizationId(String orgnizationId) {
            this.orgnizationId = orgnizationId;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
    }
}
