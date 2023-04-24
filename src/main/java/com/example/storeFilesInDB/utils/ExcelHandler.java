package com.example.storeFilesInDB.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.storeFilesInDB.entity.StudentData;

public class ExcelHandler {
	
	private static final String TYPE="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	static String[] headers= {"id","name","age","email"};
	
	static String SHEET="student_data";
	
	public static boolean hasExcelFile(MultipartFile file) {
		return TYPE.equals(file.getContentType());
	}

	
	public static List<StudentData> excelToStudents(InputStream is) {
		
		try {
		      Workbook workbook = new XSSFWorkbook(is);

		      Sheet sheet = workbook.getSheet(SHEET);
		      Iterator<Row> rows = sheet.iterator();

		      List<StudentData> studentData = new ArrayList<StudentData>();

		      int rowNumber = 0;//1
		      
		      while (rows.hasNext()) {
		        Row currentRow = rows.next();
		        
		        // skip header
		        if (rowNumber == 0) {
		          rowNumber++;
		          continue;
		        }

		        Iterator<Cell> cellsInRow = currentRow.iterator();

		        StudentData data = new StudentData();

		        int cellIdx = 0;
		        
		        while (cellsInRow.hasNext()) {
		          Cell currentCell = cellsInRow.next();

		          switch (cellIdx) {
		          
		          case 0:
		        	  data.setId((long) currentCell.getNumericCellValue());
		        	 
		            break;

		          case 1:
		        	  data.setName(currentCell.getStringCellValue());
		            break;

		          case 2:
		        	  data.setAge((int) currentCell.getNumericCellValue());
		            break;

		          case 3:
		        	  data.setEmail(currentCell.getStringCellValue());
		            break;

		          default:
		            break;
		          }

		          
		          cellIdx++;
		        }
                
		        studentData.add(data);
		      }

		      workbook.close();

		      return studentData;
		      
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		    }
		  }
		
		
		
		
		
		
//        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
//            XSSFSheet sheet = workbook.getSheet(SHEET);
//            Iterator<Row> rows = sheet.iterator();
//
//            List<StudentData> students = new ArrayList<>();
//
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//
//                // skip header row
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//
//                Iterator<Cell> cellsInRow = currentRow.iterator();
//
//                StudentData student = new StudentData();
//
//                int cellIndex = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = cellsInRow.next();
//
//                    if (cellIndex == 0) {
//                        student.setName(currentCell.getStringCellValue());
//                    } else if (cellIndex == 1) {
//                        student.setAge((int) currentCell.getNumericCellValue());
//                    } else if (cellIndex == 2) {
//                        student.setEmail(currentCell.getStringCellValue());
//                    }
//                }
//            }
//            return students;
//            
//        } catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//        
//		
//	}
	}




        
