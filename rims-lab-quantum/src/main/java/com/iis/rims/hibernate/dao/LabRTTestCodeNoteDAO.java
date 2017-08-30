package com.iis.rims.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.LabRTTestCodeNote;

public class LabRTTestCodeNoteDAO extends BaseDAO<LabRTTestCodeNote, Integer> {
	public LabRTTestCodeNote getNote(int rtTestCodeId, String rtTestCode) {
		LabRTTestCodeNote obj = this.findByUnique(Restrictions.eq("labRtTestCodeId", rtTestCodeId),
						  Restrictions.eq("rtTestCodeColumn", rtTestCode));
		return obj;
	}
	
	public List<LabRTTestCodeNote> getNotes(int rtTestCodeId) {
		return this.findByCriteria(Restrictions.eq("labRtTestCodeId", rtTestCodeId));
	}
}
