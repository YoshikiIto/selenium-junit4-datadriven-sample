package poiexample;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;

	/**
	 * データテーブルとなるExcelファイルを取得
	 *
	 * @param Path
	 *            Excelファイルの古パス
	 * @param SheetName
	 *            シート名
	 * @throws Exception
	 *             Excelファイルやブック読み込み時のエラー
	 */
	public static void setExcelFile(String Path, String SheetName) {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	/**
	 * 取得したExcelファイルから、テーブル内容を取得して格納
	 *
	 * @return テストケースのコレクション
	 */
	public static Collection<Object[]> getTestCases() {
		Object[][] obj = new Object[ExcelWSheet.getLastRowNum()][1];

		for (int i = 1; i <= ExcelWSheet.getLastRowNum(); i++) {
			Cell = ExcelWSheet.getRow(i).getCell(0);
			obj[i - 1][0] = Cell.toString();
		}
		return Arrays.asList(obj);
	}

	/**
	 * 指定したセルの内容を取得
	 *
	 * @param RowNum
	 *            行番号
	 * @param ColNum
	 *            列番号
	 * @return セル内容の文字列
	 * @throws Exception セル内容の取得に失敗
	 */
	private static String getCellData(int RowNum, int ColNum) {
		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		String CellData = Cell.getStringCellValue();
		return CellData;
	}

	/**
	 * テストケースIDと列ラベルから、該当するセルの値を取得する<br>
	 * セルの書式は、数値でなく文字列であること
	 * @param caseID テストケースID
	 * @param colLabel 列ラベル
	 * @return セル内容の文字列
	 * @throws Exception セル内容の取得に失敗
	 */
	public static String getCellValue(String caseID, String colLabel) {
		try {
			Cell = ExcelWSheet.getRow(getRowNumOf(caseID)).getCell(getColNumOf(colLabel));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String CellData = Cell.getStringCellValue();
		return CellData;
	}

	/**
	 * 指定したテストケースIDの行番号を取得する
	 * @param TestID　テストケースID
	 * @return 行番号
	 * @throws Exception 行番号の取得に失敗
	 */
	private static int getRowNumOf(String TestID) {
		try {
			for (int i = 1; i <= ExcelWSheet.getLastRowNum(); i++) {
				Cell = ExcelWSheet.getRow(i).getCell(0);
				if (Cell.getStringCellValue().equals(TestID)) {
					return i;
				}
			}
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}

	/**
	 * 指定したラベルの列番号を取得する
	 * @param colName 列ラベル
	 * @return 列番号
	 * @throws Exception 列番号の取得に失敗
	 */
	private static int getColNumOf(String colName) {
		try {
			for (int j = 1; getCellData(0, j).length() > 0; j++) {
				Cell = ExcelWSheet.getRow(0).getCell(j);
				if (Cell.getStringCellValue().equals(colName)) {
					return j;
				}
			}
		} catch (Exception e) {
			return -1;
		}
		return 0;
	}
}
