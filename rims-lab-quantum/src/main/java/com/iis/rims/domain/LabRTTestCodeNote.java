package com.iis.rims.domain;

import java.io.Serializable;

public class LabRTTestCodeNote extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String OBJECTTYPESTR = "LabRTTestCodeNote";
	
	private int labRtTestCodeNoteId;
	private int labRtTestCodeId;
	private String rtTestCodeColumn;
	private String note;
	
	public int getLabRtTestCodeNoteId() {
		return labRtTestCodeNoteId;
	}
	public void setLabRtTestCodeNoteId(int labRtTestCodeNoteId) {
		this.labRtTestCodeNoteId = labRtTestCodeNoteId;
	}
	public int getLabRtTestCodeId() {
		return labRtTestCodeId;
	}
	public void setLabRtTestCodeId(int labRtTestCodeId) {
		this.labRtTestCodeId = labRtTestCodeId;
	}
	public String getRtTestCodeColumn() {
		return rtTestCodeColumn;
	}
	public void setRtTestCodeColumn(String rtTestCodeColumn) {
		this.rtTestCodeColumn = rtTestCodeColumn;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}