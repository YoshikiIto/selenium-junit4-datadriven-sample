package poiexample;

import static poiexample.ExcelUtils.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class SearchOnGoogle {

	private static WebDriver driver;
	private String caseID;

	// DataTableを読み込む
	private static void importDataTable() {
		try {
			// Excelのデータテーブルのパスとシート名を指定
			ExcelUtils.setExcelFile("C:\\DataTable.xlsx", "Sheet1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> TestcaseIdList() {
		importDataTable();
		return getTestCases();
	}

	public SearchOnGoogle(String input) {
		this.caseID = input;
	}

	@Before
	public void before() {
		System.setProperty("webdriver.chrome.driver", "C:\\pleiades\\workspace\\webdriver\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void Test() {
		// Googleを開く
		driver.get("http://google.com");

		// Excelのシートから読み込んだ文字列を検索欄に入れる
		driver.findElement(By.id("lst-ib")).sendKeys(getCellValue(caseID, "keyword"));

		// 動作確認のため3秒待つ
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@After
	public void after() {
		 driver.quit();
	}

}
