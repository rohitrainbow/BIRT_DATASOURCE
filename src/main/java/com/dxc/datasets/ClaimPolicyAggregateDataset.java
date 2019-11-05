package com.dxc.datasets;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.dxc.model.ClaimPolicyAggregateDTO;
import com.dxc.util.Constants;
import com.dxc.util.ReadProperties;

public class ClaimPolicyAggregateDataset {
	Connection conn = null;
	Statement stmt = null;

	static final String JDBC_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	ResultSet rs = null;

	public List<ClaimPolicyAggregateDTO> getClaimPolicyAggregateDTOs(String DBPARAM,String reportingStatus, String claimType, String policySite,
			Date startDate, Date endDate) {
		List<ClaimPolicyAggregateDTO> claimPolicyAggregateDTOs = new ArrayList<ClaimPolicyAggregateDTO>();
		try {
			ReadProperties rp=new ReadProperties();
			System.out.println(DBPARAM);
			String[] dbDetails=rp.dbDetailsReader(DBPARAM);
			// STEP 2: Register JDBC driver
			Class.forName("com.ibm.db2.jcc.DB2Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbDetails[0], dbDetails[1], dbDetails[2]);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;

			if(policySite.equals("--ALL--"))
			{
			sql = Constants.ClaimPolicyAggregateQuery + "(" + reportingStatus + ")" + "AND CLM.CLAIMTYPE IN (" + claimType + ")"
					+ "AND CLM.NOTIFYRCPTDATE between '" + startDate
					+ "' AND '" + endDate + "'";
			}
			else
			{
				sql = Constants.ClaimPolicyAggregateQuery + "(" + reportingStatus + ")" + "AND CLM.CLAIMTYPE IN (" + claimType + ")"
						+ "AND CLMPOL.POLICY_SITE IN (" + policySite + ")" + "AND CLM.NOTIFYRCPTDATE between '" + startDate
						+ "' AND '" + endDate + "'";
			}
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			ClaimPolicyAggregateDTO dto = null;

			while (rs.next()) {
				// Retrieve by column name

				dto = new ClaimPolicyAggregateDTO();
				dto.setPolicyNumber(rs.getString("policyNumber"));
				dto.setClaimNumber(rs.getString("claimNumber"));
				dto.setClaimCause(rs.getString("claimCause"));
				dto.setProcessingSite(rs.getString("processingSite"));
				dto.setPolGrossAmt(amountFormatter(rs.getString("polGrossAmt")));
				dto.setPolRemBal(amountFormatter(rs.getString("polRemBal")));
				dto.setPolNetAmt(amountFormatter(rs.getString("polNetAmt")));
				dto.setInsuredGender(rs.getString("insuredGender"));
				dto.setInsResSt(rs.getString("insResSt"));
				dto.setCmAFaceAmt(amountFormatter(rs.getString("cmAFaceAmt")));
				dto.setAdbBenAmt(amountFormatter(rs.getString("adbBenAmt")));
				dto.setPuaAmt(amountFormatter(rs.getString("puaAmt")));
				dto.setCsaFaceAmt(amountFormatter(rs.getString("csaFaceAmt")));
				dto.setPolFaceAmountPaid(amountFormatter(rs.getString("polFaceAmountPaid")));
				dto.setPolAdbAmountPaid(amountFormatter(rs.getString("polAdbAmountPaid")));
				dto.setPuaAmountPaid(amountFormatter(rs.getString("puaAmountPaid")));
				dto.setPremAdjustPaid(amountFormatter(rs.getString("premAdjustPaid")));
				dto.setDividendsPaid(amountFormatter(rs.getString("dividendsPaid")));
				dto.setLoanWithheld(amountFormatter(rs.getString("loanWithheld")));
				dto.setInterestPaid(amountFormatter(rs.getString("interestPaid")));
				dto.setClaimFlag(getFlag(rs.getString("ClmFlagCONTESTABLE"), rs.getString("ClmFlagCOMPROMISED"),
						rs.getString("ClmFlagUNDERLEGALREVIEW"), rs.getString("ClmFlagPAIDTOINTERPLEADER"),
						rs.getString("ClmFlagCOMMONDISASTERCLAUSE"), rs.getString("ClmFlagBPO_UNDERINVESTIGATION"),
						rs.getString("ClmFlagBPO_CLAIMDEFERRED"), rs.getString("ClmFlagBPO_VIATICALCLAIM"),
						rs.getString("ClmFlagBPO_CLIENTREVIEWREQUIRED")));
				dto.setPolicyFlag(getFlag(rs.getString("PolFlagCONTESTABLE"), rs.getString("PolFlagCOMPROMISED"),
						rs.getString("PolFlagUNDERLEGALREVIEW"), rs.getString("PolFlagPAIDTOINTERPLEADER"),
						rs.getString("PolFlagCOMMONDISASTERCLAUSE"), rs.getString("PolFlagBPO_UNDERINVESTIGATION"),
						rs.getString("PolFlagBPO_CLAIMDEFERRED"), rs.getString("PolFlagBPO_VIATICALCLAIM"),
						rs.getString("PolFlagBPO_CLIENTREVIEWREQUIRED")));
				dto.setInsLastName(getInsuredLastName(rs.getString("CLIENT_TYPE_ID"),
						rs.getString("SEARCH_COMPANY_NAME"), rs.getString("SEARCH_LAST_NAME")));
				dto.setRegion(rs.getString("region"));
				dto.setCompany(rs.getString("company"));
				dto.setInsFirst(rs.getString("insFirst"));
				dto.setInsMiddle(rs.getString("insMiddle"));
				dto.setClaimType(rs.getString("claimType"));
				dto.setInsInd(rs.getString("insInd"));
				dto.setMajorLob(rs.getString("majorLob"));
				dto.setPayAdj(rs.getString("payAdj"));
				dto.setSplHandleCode(rs.getString("splHandleCode"));
				dto.setManner(rs.getString("manner"));
				dto.setCauseOfDeath(rs.getString("causeOfDeath"));
				dto.setNotifiedDate(dateFormatter(rs.getString("notifiedDate")));
				dto.setDatePolicyEntered(dateFormatter(rs.getString("datePolicyEntered")));
				dto.setIssueDate(dateFormatter(rs.getString("issueDate")));
				dto.setIssueState(rs.getString("issueState"));
				dto.setClosedDate(dateFormatter(rs.getString("closedDate")));
				dto.setClaimStatus(rs.getString("claimStatus"));
				dto.setExaminer(rs.getString("examiner"));
				dto.setDatePolicyReported(dateFormatter(rs.getString("datePolicyReported")));
				dto.setDateOfLoss(dateFormatter(rs.getString("dateOfLoss")));
				dto.setCyberPaidDate(dateFormatter(rs.getString("cyberPaidDate")));
				dto.setPolRptngStatus(rs.getString("polRptngStatus"));
				dto.setPolicyStatus(rs.getString("policyStatus"));
				dto.setPolicyManagementStatus(rs.getString("policyManagementStatus"));
				dto.setProductType(rs.getString("productType"));
				dto.setReportingProductType(rs.getString("reportingProductType"));
				dto.setProductSubtype(rs.getString("productSubtype"));
				dto.setPlanCode(rs.getString("planCode"));
				dto.setParType(rs.getString("parType"));
				dto.setIssueAge(rs.getString("issueAge"));
				dto.setBeagleInd(rs.getString("beagleInd"));
				
				dto.setBulkCode(rs.getString("bulkCode"));

				claimPolicyAggregateDTOs.add(dto);
				// Display values

			}

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

		// end main
		// end FirstExample
		System.out.println(claimPolicyAggregateDTOs.size());

		return claimPolicyAggregateDTOs;
	}

	public String getFlag(String contestable, String compromised, String underlegalreview, String paidtointerpleader,
			String commondisasterclause, String bpo_underinvestigation, String bpo_claimdeferred,
			String bpo_viaticalclaim, String bpo_clientreviewrequired) {

		String flags = " ";
		if (contestable.equals("1")) {
			flags = flags + "C";
		}
		if (compromised.equals("1")) {
			flags = flags + "Y";
		}
		if (underlegalreview.equals("1")) {
			flags = flags + "L";
		}
		if (paidtointerpleader.equals("1")) {
			flags = flags + "Z";
		}
		if (commondisasterclause.equals("1")) {
			flags = flags + "X";
		}
		if (bpo_underinvestigation.equals("1")) {
			flags = flags + "I";
		}
		if (bpo_claimdeferred.equals("1")) {
			flags = flags + "D";
		}
		if (bpo_viaticalclaim.equals("1")) {
			flags = flags + "V";
		}
		if (bpo_clientreviewrequired.equals("1")) {
			flags = flags + "R";
		}

		// System.out.println("Flag : " + flags);
		return flags;

	}

	public String getInsuredLastName(String clientTypeId, String searchCompanyName, String searchLastName) {
		String insuredName = "";

		if (clientTypeId != null) {
			if (clientTypeId.equals("2"))
				insuredName = searchCompanyName;
			else {
				insuredName = searchLastName;
			}
		} else {
			insuredName = searchLastName;
		}
		return insuredName;
	}

	public String dateFormatter(String input) throws ParseException {
		String[] dateBrk = new String[3];
		if (input != null) {
			dateBrk = input.split("-");
			if (dateBrk.length == 3) {

				return dateBrk[1] + "/" + dateBrk[2] + "/" + dateBrk[0];
			} else
				return "";
		} else
			return "";
	}

	public String amountFormatter(String input) {
		if (input != null && Double.parseDouble(input) != 0) {
			double amount = Double.parseDouble(input);
			DecimalFormat formatter = new DecimalFormat("#,##0.00");
			return formatter.format(amount);
		} else
			return "0.00";
	}
}
