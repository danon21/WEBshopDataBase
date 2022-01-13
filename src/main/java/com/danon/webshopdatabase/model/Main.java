package com.danon.webshopdatabase.model;

import com.danon.webshopdatabase.repositories.IRepositoryGoods;
import com.danon.webshopdatabase.repositories.IRepositoryOrders;
import com.danon.webshopdatabase.repositories.IRepositoryUsers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {

    //Приводит дату к заданному формату
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    //Создаем массивы, в которых будут хранится экземпляры классов
    private static final ArrayList<CUser> users = new ArrayList<>();
    private static final ArrayList<CGood> goods = new ArrayList<>();
    private static final ArrayList<COrder> orders = new ArrayList<>();

    @Autowired
    private static IRepositoryUsers repositoryUsers;
    @Autowired
    private static IRepositoryGoods repositoryGoods;
    @Autowired
    private static IRepositoryOrders repositoryOrders;

    //Метод загрузки товаров из файла
    public static void LoadGoods(File file) {
        try (XSSFWorkbook wb = new XSSFWorkbook(file)) {

            Sheet sheet = wb.getSheet("Товары"); //Переменная в которой хранится Excel лист
            Integer rows = sheet.getLastRowNum();

            Row row;
            Cell cell;
            UUID id;
            String temp, name, category;
            double price;

            //перебираем все товары
            for (int i = 1; i <= rows; i++) {

                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                //ID товара
                cell = row.getCell(0);
                temp = cell.getStringCellValue();
                id = UUID.fromString(temp);

                //Имя
                cell = row.getCell(1);
                name = cell.getStringCellValue();

                //Стоимость
                cell = row.getCell(2);
                price = cell.getNumericCellValue();

                //Категория
                cell = row.getCell(3);
                category = cell.getStringCellValue();

                //Добавляем в массив с товарами экземпляр класса "Товары"
                goods.add(new CGood(id, name, price, category));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Метод загрузки покупок из файла
    public static void LoadOrder(File file) {
        Map<UUID, CUser> usersDict = new HashMap<UUID, CUser>();
        Map<UUID, CGood> goodsDict = new HashMap<UUID, CGood>();

        for (CUser user : users) {
            usersDict.put(user.getId(), user);
        }
        for (CGood good : goods) {
            goodsDict.put(good.getId(), good);
        }
        try (XSSFWorkbook wb = new XSSFWorkbook(file)) {

            Sheet sheet = wb.getSheet("Покупки"); //Переменная в которой хранится Excel лист
            Integer rows = sheet.getLastRowNum();
            Row row;
            String temp;
            Cell cell;
            UUID userID, goodID, orderID;
            LocalDate purchaseDate;

            for (int i = 1; i <= rows; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //ID заказа
                cell = row.getCell(0);
                temp = cell.getStringCellValue();
                orderID = UUID.fromString(temp);
                //ID пользователя
                cell = row.getCell(1);
                temp = cell.getStringCellValue();
                userID = UUID.fromString(temp);

                //ID товара
                cell = row.getCell(2);
                temp = cell.getStringCellValue();
                goodID = UUID.fromString(temp);

                //Дата покупки
                cell = row.getCell(3);
                purchaseDate = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                orders.add(new COrder(orderID, usersDict.get(userID), goodsDict.get(goodID), purchaseDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Метод загрузки пользователей
    public static void LoadUsers(File file) {
        try (XSSFWorkbook wb = new XSSFWorkbook(file)) {
            Sheet sheet = wb.getSheet("Пользователи"); //Переменная в которой хранится Excel лист
            Integer rows = sheet.getLastRowNum(); //Количество строк в листе - хранится номер последней строки
            Row row;
            Cell cell;
            String temp, login, name;
            UUID id;
            boolean sex = false;
            LocalDate dateBirth;

            //Цикл перебора строк
            for (int i = 1; i <= rows; i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //ID пользователя
                cell = row.getCell(0);
                temp = cell.getStringCellValue();
                id = UUID.fromString(temp);

                //Логин пользователя
                cell = row.getCell(1);
                login = cell.getStringCellValue();

                //Имя пользователя
                cell = row.getCell(2);
                name = cell.getStringCellValue();

                //Пол
                cell = row.getCell(3);
                if ("м" == cell.getStringCellValue()) {
                    sex = true;
                }

                //Дата рождения
                cell = row.getCell(4);
                dateBirth = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                //Добавляем в массив с пользователями новый экземпляр класса "Пользователи"
                users.add(new CUser(id, login, name, dateBirth, sex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        File file = new File("D:\\study\\java\\ShopDataBaseData", "Magazin.xlsx");
        LoadUsers(file);
        LoadGoods(file);
        LoadOrder(file);

        if(users.size() != 0){
            repositoryUsers.saveAll(users);
        }
        if(goods.size() != 0){
           repositoryGoods.saveAll(goods);
        }
        if(orders.size() != 0){
            repositoryOrders.saveAll(orders);
        }


    }

    public static void CreateWordReport(Map<String, Integer> dict) {
        //Настраиваем верхний параграф - заголовок
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER); //ориентация по центру
        p1.setVerticalAlignment(TextAlignment.TOP); //ориентация сверху
        //Записываем заголовок
        XWPFRun r1 = p1.createRun();
        r1.setBold(true); //шрифт "жирный"
        r1.setText("Отчет по продажам товаров."); //записываем текст параграфа
        r1.setFontFamily("TimesNewRoman"); //устанавливаем шрифт
        r1.setFontSize(16); //устанавливаем размер шрифта
        r1.setUnderline(UnderlinePatterns.SINGLE); //подчеркиваем одной линией

        XWPFParagraph p2 = doc.createParagraph();
        XWPFRun r2 = p2.createRun();
        r2.setText("Категория: \"Продукты питания\"");
        r2.setFontFamily("TimesNewRoman");
        r2.setFontSize(14);
        //Создаем таблицу
        XWPFTable table = doc.createTable();
        //Заполняем данными
        XWPFTableRow row;
        row = table.getRow(0);
        row.getCell(0).setText("Наименование товара");
        row.addNewTableCell().setText("Кол-во продаж");
        for (Map.Entry<String, Integer> entry : dict.entrySet()) {
            row = table.createRow();
            row.getCell(0).setText(entry.getKey().toString());
            row.getCell(1).setText(entry.getValue().toString());
        }
        table.setWidth("100%"); // Таблица шириной на весь экран
        //Записываем отчет в файл
        try (FileOutputStream out = new FileOutputStream("D:\\study\\java\\WEBShopDataBaseData\\Отчет_КоличествоПроданныхПродуктовПитания.docx")) {
            doc.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Метод для составления отчетов
    public static void report() {

        //Запрашиваем списки товаров и заказов из БД
        List<COrder> orders = repositoryOrders.findAll();
        List<CGood> goods = repositoryGoods.findAll();

        int CountPurchases = orders.size(); //количество заказов
        ArrayList<UUID> Purchases = new ArrayList<>(); //Массив UUID всех купленных товаров
        int CountGoods = goods.size(); //количество товаров
        int counter; //счетчик товаров
        UUID GoodUUID; // переменная, в которой будет лежать UUID товара
        Map<String, Integer> dict = new HashMap<String, Integer>(); //словарь: Товар:Кол-во

        //Формируем массив с UUID всех купленных товаров
        for (int i = 0; i < CountPurchases; i++) {
            Purchases.add(orders.get(i).getGood().getId());
        }
        //Формируем словар
        for (int i = 0; i < CountGoods; i++) {
            if (goods.get(i).getCategory().equals("Продукты питания")) {
                GoodUUID = goods.get(i).getId();
                counter = 0;

                for (int j = 0; j < CountPurchases; j++) {
                    if (GoodUUID.equals(Purchases.get(j))) {
                        counter = counter + 1;
                    }
                }
                dict.put(goods.get(i).getGoodName(), counter);
            }
        }
        CreateWordReport(dict);
    }

    public static void main(String[] args) {}
}