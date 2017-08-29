package com.iis.rims.hibernate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.ReturningWork;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.LabRtTestCode;
import com.iis.rims.domain.dto.lab.LabResultDTO;
import com.iis.rims.domain.dto.lab.LabRtTestCodeDTO;

public class LabRtTestCodeDAO extends BaseDAO<LabRtTestCode, Integer> {
	private static final String RT_CODE_TABLE_NAME = "lab.tbl_LabRTTestCode";
	private static final String PATIENT_TABLE_NAME = "mst.tbl_Patient";
	private static final String CUSTOMER_TABLE_NAME = "mst.tbl_Customer";
	private static final String LAB_ORDER_DETAIL_TABLE_NAME = "his.tbl_LabOrderDetail";
	
	private static final List<String> EXCLUDE_COLUMNS = Arrays.asList("LastUpdatedBy", "LastUpdatedDate",
									"EntityStatus", "CreatedDate", "CreatedBy",
									"OrganizationId", "BranchId", "LabCustomerId", "PatientId");
	private static final List<String> INFO_COLUMNS = Arrays.asList("LabName", "PatientName", "NricNumber", 
							"DateOfBirth", "LabResultDate", "OrderNumberRef", "LabRtTestCodeId", "LabOrderDetailId", "LabOrderId");
	private static final Map<Integer, String> RESULT_FIELD_MAPPING = new HashMap<Integer, String>();
	
	static {
		RESULT_FIELD_MAPPING.put(0, "observationValue");
		RESULT_FIELD_MAPPING.put(1, "units");
		RESULT_FIELD_MAPPING.put(2, "referenceRange");
		RESULT_FIELD_MAPPING.put(3, "abnormalFlags");
		RESULT_FIELD_MAPPING.put(4, "observationStatus");
	}
	
	public int updateLabResult(final int organizationId, final int branchId, final int labCustomerId, final int labOrderDetailId,
			final int patientId, final String nricNumber, final Collection<String> rtTestCodes, final Collection<String> results) {
		
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Integer ret = session.doReturningWork(new ReturningWork<Integer>() {
				private Integer isResultExisted(Connection connection) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet rs = null;
					try {
						String sql = String.format("SELECT LabRtTestCodeId FROM %s WHERE LabOrderDetailId = ?", RT_CODE_TABLE_NAME);
						stmt = connection.prepareStatement(sql);
						stmt.setInt(1, labOrderDetailId);
						rs = stmt.executeQuery();
						if (rs.next()) {
							return rs.getInt(1);
						}
						return null;
					}
					finally {
						if (rs != null) {
							rs.close();
						}
						if (stmt != null) {
							stmt.close();
						}
					}
				}
				
				@Override
				public Integer execute(Connection connection) throws SQLException {
					
					PreparedStatement stmt = null;
					try {
						String sql = null;
						Integer labRtTestCodeId = isResultExisted(connection);
						boolean resultExisted = labRtTestCodeId != null;
						if (resultExisted) {
							List<String> fields = new ArrayList<>();
							for (String code : rtTestCodes) {
								fields.add(String.format("%s=?", code));
							}
							sql = String.format("UPDATE %s SET OrganizationId=?, BranchId=?, LabCustomerId=?, "
									+"PatientId=?, NricNumber=?, %s, EntityStatus=?, LastUpdatedBy=?, LastUpdatedDate=? "
									+ "WHERE LabOrderDetailId = ?", RT_CODE_TABLE_NAME, StringUtils.join(fields, ","));
						}
						else {
							String rtTestColumnNames = StringUtils.join(rtTestCodes, ",");
							String parameters = StringUtils.repeat(" ?,", rtTestCodes.size());
							if (parameters.length() > 0) {
								parameters = parameters.substring(0, parameters.length() - 1);
							}
							sql = String.format("INSERT INTO %s (OrganizationId, BranchId, LabCustomerId, "
									+"PatientId, NricNumber, %s, EntityStatus, CreatedBy, CreatedDate, LastUpdatedBy, LastUpdatedDate, LabOrderDetailId) "
									+ "VALUES (?, ?, ?, ?, ?, %s, ?, ?, ?, ?, ?, ?) ", RT_CODE_TABLE_NAME, rtTestColumnNames, parameters);
						}
						if (resultExisted) {
							stmt = connection.prepareStatement(sql);
						}
						else {
							stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						}
						int index = 1;
						stmt.setInt(index++, organizationId);
						stmt.setInt(index++, branchId);
						stmt.setInt(index++, labCustomerId);
						stmt.setInt(index++, patientId);
						stmt.setString(index++, nricNumber);
						for (String value : results) {
							stmt.setString(index++, value);
						}
						Date today = new Date();
						stmt.setInt(index++, 1);
						stmt.setInt(index++, 1);
						if (!resultExisted) {
							stmt.setTimestamp(index++, new Timestamp(today.getTime()));
							stmt.setInt(index++, 1);
						}
						stmt.setTimestamp(index++, new Timestamp(today.getTime()));
						stmt.setInt(index++, labOrderDetailId);
						int ret = stmt.executeUpdate();
						if (ret > 0) {
							if (!resultExisted) {
								ResultSet rs = stmt.getGeneratedKeys();
								if (rs.next()) {
									labRtTestCodeId = rs.getInt(1);
								}
								rs.close();
							}
							return labRtTestCodeId;
						}
						
						return -1;
					}
					finally {
						if (stmt != null) {
							stmt.close();
						}
					}
				}
			});
			transaction.commit();
			return ret;
			
		} catch (RuntimeException re) {
			transaction.rollback();
			throw re;
		}
	}
	
	@Deprecated
	public Map<String, Object> getRtTestCodeData(final int labOrderDetailId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Map<String, Object> ret = session.doReturningWork(new ReturningWork<Map<String, Object>>() {

				@Override
				public Map<String, Object> execute(Connection connection) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet rs = null;
					try {
						// TODO This query will be replaced by the stored procedure.
						String sql = String.format("SELECT rt.*, p.PatientName, c.CustomerName AS LabName, l.LabResultDate "
								+ " FROM %s rt, %s p, %s c, %s l "
								+ " WHERE labOrderDetailId = %s AND rt.patientId = p.patientId"
								+ " AND c.customerId = rt.LabCustomerId AND rt.labOrderDetailId = l.labOrderDetailId ", 
								RT_CODE_TABLE_NAME, PATIENT_TABLE_NAME, CUSTOMER_TABLE_NAME, LAB_ORDER_DETAIL_TABLE_NAME, labOrderDetailId);
						stmt = connection.prepareStatement(sql);
						rs = stmt.executeQuery();
						ResultSetMetaData r = rs.getMetaData();
						if (rs.next()) {
							Map<String, Object> ret = new LinkedHashMap<String, Object>();
							for (int i = 1; i <= r.getColumnCount(); i++) {
								if (!EXCLUDE_COLUMNS.contains(r.getColumnName(i))) {
									ret.put(r.getColumnName(i), rs.getObject(i));
								}
							}
							return ret;
						}
						return null;
					}
					finally {
						if (rs != null) {
							rs.close();
						}
						if (stmt != null) {
							stmt.close();
						}
					}
				}
				
			});
			transaction.commit();
			return ret;
		}
		catch (RuntimeException re) {
			transaction.rollback();
			throw re;
		}
	}
	
	public LabRtTestCodeDTO getRtTestCodeDTO(final int labOrderDetailId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			LabRtTestCodeDTO ret = session.doReturningWork(new ReturningWork<LabRtTestCodeDTO>() {

				@Override
				public LabRtTestCodeDTO execute(Connection connection) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet rs = null;
					try {
						// TODO This query will be replaced by the stored procedure.
						String sql = String.format("SELECT rt.*, p.PatientName, p.DateOfBirth AS DateOfBirth, c.CustomerName AS LabName, l.LabResultDate, l.OrderNumberRef "
								+ " FROM %s rt, %s p, %s c, %s l"
								+ " WHERE rt.labOrderDetailId = %s AND rt.patientId = p.patientId"
								+ " AND c.customerId = rt.LabCustomerId AND rt.LabOrderDetailId = l.LabOrderDetailId", 
								RT_CODE_TABLE_NAME, PATIENT_TABLE_NAME, CUSTOMER_TABLE_NAME, LAB_ORDER_DETAIL_TABLE_NAME, labOrderDetailId);
						stmt = connection.prepareStatement(sql);
						rs = stmt.executeQuery();
						ResultSetMetaData r = rs.getMetaData();
						if (rs.next()) {
							LabRtTestCodeDTO ret = new LabRtTestCodeDTO();
							try {
								for (int i = 1; i <= r.getColumnCount(); i++) {
									String columnName = r.getColumnName(i);
									if (!EXCLUDE_COLUMNS.contains(columnName)) {
										if (INFO_COLUMNS.contains(r.getColumnName(i))) {
											columnName = columnName.substring(0, 1).toLowerCase() + columnName.substring(1);
											FieldUtils.writeField(ret, columnName, rs.getObject(i), true);
										}
										else {
											String resultValue = rs.getString(i);
											if (!StringUtils.isEmpty(resultValue)) {
												LabResultDTO result = new LabResultDTO();
												result.setRtTestCodeColumn(columnName);
												String[] values = resultValue.split("\\|");
												for (int index = 0; index < values.length; index++) {
													String fieldName = RESULT_FIELD_MAPPING.get(index);
													String value = values[index];
													if (fieldName != null && !StringUtils.isEmpty(value)) {
														FieldUtils.writeField(result, fieldName, value, true);
													}
												}
												
												ret.addLabResult(result);
											}
										}
									}
								}
							}
							catch (Exception ex) {
								throw new RuntimeException(ex);
							}
							return ret;
						}
						return null;
					}
					finally {
						if (rs != null) {
							rs.close();
						}
						if (stmt != null) {
							stmt.close();
						}
					}
				}
				
			});
			transaction.commit();
			return ret;
		}
		catch (RuntimeException re) {
			transaction.rollback();
			throw re;
		}
	}
	
	public List<LabRtTestCodeDTO> getRtTestCodeDTOs(final List<Integer> labOrderIds) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<LabRtTestCodeDTO> ret = session.doReturningWork(new ReturningWork<List<LabRtTestCodeDTO>>() {

				@Override
				public List<LabRtTestCodeDTO> execute(Connection connection) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet rs = null;
					try {
						// TODO This query will be replaced by the stored procedure.
						String inList = StringUtils.join(labOrderIds, ",");
						String sql = String.format("SELECT rt.*, p.PatientName, p.DateOfBirth AS DateOfBirth, " 
								+ " c.CustomerName AS LabName, l.LabResultDate, l.LabOrderDetailId, l.LabOrderId "
								+ " FROM %s rt, %s p, %s c, %s l "
								+ " WHERE l.labOrderId IN ( %s ) AND rt.patientId = p.patientId "
								+ " AND c.customerId = rt.LabCustomerId AND rt.LabOrderDetailId = l.LabOrderDetailId ", 
								RT_CODE_TABLE_NAME, PATIENT_TABLE_NAME, CUSTOMER_TABLE_NAME, LAB_ORDER_DETAIL_TABLE_NAME, inList);
						stmt = connection.prepareStatement(sql);
						rs = stmt.executeQuery();
						ResultSetMetaData r = rs.getMetaData();
						List<LabRtTestCodeDTO> list = new ArrayList<LabRtTestCodeDTO>();
						while (rs.next()) {
							LabRtTestCodeDTO obj = new LabRtTestCodeDTO();
							try {
								for (int i = 1; i <= r.getColumnCount(); i++) {
									String columnName = r.getColumnName(i);
									if (!EXCLUDE_COLUMNS.contains(columnName)) {
										if (INFO_COLUMNS.contains(r.getColumnName(i))) {
											columnName = columnName.substring(0, 1).toLowerCase() + columnName.substring(1);
											FieldUtils.writeField(obj, columnName, rs.getObject(i), true);
										}
										else {
											String resultValue = rs.getString(i);
											if (!StringUtils.isEmpty(resultValue)) {
												LabResultDTO result = new LabResultDTO();
												result.setRtTestCodeColumn(columnName);
												String[] values = resultValue.split("\\|");
												for (int index = 0; index < values.length; index++) {
													String fieldName = RESULT_FIELD_MAPPING.get(index);
													String value = values[index];
													if (fieldName != null && !StringUtils.isEmpty(value)) {
														FieldUtils.writeField(result, fieldName, value, true);
													}
												}
												
												obj.addLabResult(result);
											}
										}
									}
								}
								list.add(obj);
							}
							catch (Exception ex) {
								throw new RuntimeException(ex);
							}
						}
						return list;
					}
					finally {
						if (rs != null) {
							rs.close();
						}
						if (stmt != null) {
							stmt.close();
						}
					}
				}
				
			});
			transaction.commit();
			return ret;
		}
		catch (RuntimeException re) {
			transaction.rollback();
			throw re;
		}
	}
	
	public Boolean createNewColumn(final String rtTestCodeColumn) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Boolean ret = getSession().doReturningWork(new ReturningWork<Boolean>() {

				@Override
				public Boolean execute(Connection connection) throws SQLException {
					Boolean ret = false;
					Statement stmt = null;
					try {
						String sql = String.format("ALTER TABLE %s "
								   + "ADD %s varchar(200)", RT_CODE_TABLE_NAME, rtTestCodeColumn);
						
						stmt = connection.createStatement();
						stmt.execute(sql);
						ret = true;
						
					}
					finally {
						if (stmt != null) {
							stmt.close();
						}
					}
					return ret;
				}
			});
			transaction.commit();
			return ret;
			
		} catch (RuntimeException re) {
			transaction.rollback();
			throw re;
		}
	}
	
	public Boolean updateLinkLabResult(final int linkLabOrderDetailId, final int labOrderDetailId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Boolean ret = getSession().doReturningWork(new ReturningWork<Boolean>() {

				@Override
				public Boolean execute(Connection connection) throws SQLException {
					Boolean ret = false;
					Statement stmt = null;
					try {
						String sql = String.format("UPDATE %s "
								   + "SET LabOrderDetailId = %d WHERE LabOrderDetailId = %d", RT_CODE_TABLE_NAME, labOrderDetailId, linkLabOrderDetailId);
						
						stmt = connection.createStatement();
						stmt.execute(sql);
						ret = true;
						
					}
					finally {
						if (stmt != null) {
							stmt.close();
						}
					}
					return ret;
				}
			});
			transaction.commit();
			return ret;
			
		} catch (RuntimeException re) {
			transaction.rollback();
			throw re;
		}
	}
}
