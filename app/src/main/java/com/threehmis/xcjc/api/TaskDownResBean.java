package com.threehmis.xcjc.api;

import java.util.List;

/**
 * Created by llz on 2018/2/2.
 */

public class TaskDownResBean {


    /**
     * total : 2
     * rows : [{"id":"27bee317eaf74acf94c0e68bdb1f8998","gcName":"测试合同流程用的工程","jcParam":"抗折","jcNum":"22"},{"id":"de51e71ea0294598a0f625b78104eb7d","gcName":"测试单位及人员关联工程","jcParam":"砼强度","jcNum":"111"}]
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 27bee317eaf74acf94c0e68bdb1f8998
         * gcName : 测试合同流程用的工程
         * jcParam : 抗折
         * jcNum : 22
         */

        private String id;
        private String gcName;
        private String jcParam;
        private String jcNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGcName() {
            return gcName;
        }

        public void setGcName(String gcName) {
            this.gcName = gcName;
        }

        public String getJcParam() {
            return jcParam;
        }

        public void setJcParam(String jcParam) {
            this.jcParam = jcParam;
        }

        public String getJcNum() {
            return jcNum;
        }

        public void setJcNum(String jcNum) {
            this.jcNum = jcNum;
        }
    }
}
