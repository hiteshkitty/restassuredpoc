package com.kits.controller;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kits.Utils.ExcelUtils;
import com.kits.base.BaseTest;
import com.kits.dbutil.DBUtil;
import com.kits.model.User;

public class UserControllerTest extends BaseTest {

	private User user;
	
	@Before
	public  void setup() {
		user = new User();
		user.setFirstName("Savar");
		user.setLastName("Sharma");
	}
	//@Test
	public void test() {
		given().get("/users/3").then().statusCode(200);
		User user = fetchUserDataById(3);
		System.out.println(user);
	}

	
	@Test
	public void testCreateUser() throws JsonProcessingException {
		
		Object[][] testData = getPostData();

		for (int i = 0; i < testData.length ; i ++)  {
			Map<String, Object> map = new HashMap<>();
			// You can convert any Object.

			map.put("firstName", testData[i][0]);
			map.put("lastName", testData[i][1]);

			String json = new ObjectMapper().writeValueAsString(map);
			System.out.println(json);

			given().body(json).contentType("application/json").when().log().all().post("/users/").then().statusCode(200);
		}
//		Map<String, Object> map = new HashMap<>();
//		// You can convert any Object.
//
//		map.put("firstName", "Raghav");
//		map.put("lastName", "CEO");
//
//		String json = new ObjectMapper().writeValueAsString(map);
//		System.out.println(json);
//
//		given().body(json).contentType("application/json").when().log().all().post("/users/").then().statusCode(200);
		
	}
	
	@DataProvider(name = "input")
	public Object[][] getPostData() {
		Object[][] data = null;
		ExcelUtils xlReader = new ExcelUtils();
		HashMap<String, ArrayList<Object>> map = xlReader.getExcelData();
		
		ArrayList<Object> value = map.get("firstName");
		data = new Object[value.size()][2];

		for (int i = 0; i < value.size(); i++) {
			data[i][0]=map.get("firstName").get(i);
			data[i][1]=map.get("lastName").get(i);
		}
		return data;
	}

	
	private User fetchUserDataById(int id) {
		Connection con = DBUtil.getConnection();
		User user = new User();

		if (con != null)
			System.out.println("JDBC:connection is taken..");
		else
			System.out.println("Not able to fetch connection");

		Statement st = null;
		ResultSet rs = null;
		try {
			// 3.create the Required JDBC statement
			st = con.createStatement();
			// 4.prepare the Required SQL statement
			String sql = "select * from users where id = " + id;
			System.out.println(sql);
			// 5.submit the sql statement to DB
			rs = st.executeQuery(sql);

			if (rs != null) {
				rs.next();
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.cleanup(con, st, rs);
		}
		return user;
	}
}
