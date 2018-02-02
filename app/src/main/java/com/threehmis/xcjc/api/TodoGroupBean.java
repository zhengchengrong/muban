package com.threehmis.xcjc.api;

public class TodoGroupBean {


	/**
	 * bean : {"taskAssignCount":0,"investigationCount":0,"detectionCount":0}
	 */

	private BeanBean bean;

	public BeanBean getBean() {
		return bean;
	}

	public void setBean(BeanBean bean) {
		this.bean = bean;
	}

	public static class BeanBean {
		/**
		 * taskAssignCount : 0
		 * investigationCount : 0
		 * detectionCount : 0
		 */

		private int taskAssignCount;
		private int investigationCount;
		private int detectionCount;

		public int getTaskAssignCount() {
			return taskAssignCount;
		}

		public void setTaskAssignCount(int taskAssignCount) {
			this.taskAssignCount = taskAssignCount;
		}

		public int getInvestigationCount() {
			return investigationCount;
		}

		public void setInvestigationCount(int investigationCount) {
			this.investigationCount = investigationCount;
		}

		public int getDetectionCount() {
			return detectionCount;
		}

		public void setDetectionCount(int detectionCount) {
			this.detectionCount = detectionCount;
		}
	}
}
