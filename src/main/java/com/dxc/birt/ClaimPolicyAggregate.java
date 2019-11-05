package com.dxc.birt;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dxc.datasets.ClaimPolicyAggregateDataset;
import com.dxc.model.ClaimPolicyAggregateDTO;
import com.dxc.util.Constants;
import com.dxc.util.DateConverter;
import com.dxc.validators.InputValidator;

public class ClaimPolicyAggregate {

	public Iterator<ClaimPolicyAggregateDTO> iterator;

	public void open(Object appContext, Map<String, Object> inputParameters) throws SQLException, ParseException {
		ClaimPolicyAggregateDataset cds = new ClaimPolicyAggregateDataset();
		String DBPARAM= (String) inputParameters.get("DBPARAM");
		System.out.println(DBPARAM);
		String reportingStatus = (String) inputParameters.get("reportingStatus");
		System.out.println(reportingStatus);
		String claimType = (String) inputParameters.get("claimType");
		String policySite = (String) inputParameters.get("policySite");
		String intrStartDate = (String) inputParameters.get("startDate");
		System.out.println("Start :" + intrStartDate);
		String intrEndDate = (String) inputParameters.get("endDate");
		InputValidator inputValidator = new InputValidator();
		List<ClaimPolicyAggregateDTO> ClaimPolicyAggregateDTOs = new ArrayList<ClaimPolicyAggregateDTO>();
		if (inputValidator.dateValidator(intrStartDate) && inputValidator.dateValidator(intrEndDate)
				&& inputValidator.dateCheck(intrStartDate, intrEndDate)) {
			DateConverter dc = new DateConverter();
			Date startDate = dc.converter(intrStartDate);
			Date endDate = dc.converter(intrEndDate);

			ClaimPolicyAggregateDTOs = cds.getClaimPolicyAggregateDTOs(DBPARAM,reportingStatus, claimType, policySite, startDate, endDate);
		}
		iterator = ClaimPolicyAggregateDTOs.iterator();
	}

	public Object next() {
		if (iterator.hasNext())
			return iterator.next();
		return null;
	}

	public void close() {
		iterator = null;
	}

	public static void main(String args[]) throws SQLException, ParseException {
		ClaimPolicyAggregateDataset ads = new ClaimPolicyAggregateDataset();
		String intrStartDate = "2018-01-01";
		String intrEndDate = "2019-01-01";
		InputValidator iv = new InputValidator();
		if (iv.dateValidator(intrStartDate) && iv.dateValidator(intrEndDate)
				&& iv.dateCheck(intrStartDate, intrEndDate)) {
			DateConverter dc = new DateConverter();
			Date startDate = dc.converter(intrStartDate);
			Date endDate = dc.converter(intrEndDate);
			System.out.println(startDate);
		//	System.out.println(endDate);
			ads.getClaimPolicyAggregateDTOs("PRU","1,2,3,4,5", "0,1,2", "--ALL--", startDate, endDate);

			System.out.println(Constants.ClaimPolicyAggregateQuery);
		} else
			System.out.println("Invalid input");
		// ClaimPolicyAggregateDataset ads =new ClaimPolicyAggregateDataset();
		System.out.println(ads.amountFormatter("8245945555454737.00"));
	}

}
