package com.akbas.spring.CompareTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class Baglanti {

	private String kullanici_adi = "root";
	private String parola = "hus01988";
	private String db_ad = "datetime";
	private String host = "localhost";
	private int port = 3306;

	private Connection con = null;

	private Statement statement = null;

	public Baglanti() {

		String url = "jdbc:mysql://" + host + ":" + port + "/" + db_ad;

		try {

			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			System.out.println("Driver bulunamadı");
		}

		try {
			con = DriverManager.getConnection(url, kullanici_adi, parola);
			// System.out.println("Bağlantı başarılı...");

		} catch (SQLException e) {
			System.out.println("Bağlantı başarısız...");
		}

	}

	public void bilgiGetir(Time siparis) {

		String sorgu = "Select baslangicSaati, bitisSaati From datetime.worktime;";

		try {
			statement = con.createStatement();

			ResultSet rs = statement.executeQuery(sorgu);
			while (rs.next()) {
				Time acilis = rs.getTime("baslangicSaati");
				Time kapanis = rs.getTime("bitisSaati");
				System.out.println("Açılış saati: " + acilis);
				System.out.println("Kapanış saati: " + kapanis);

				if ((acilis.before(siparis)) && (kapanis.after(siparis))) {
					System.out.println("Sipariş verilebilir.");

				}

				else {

					System.out.println("Sipariş saatleri dışındayız.");
				}

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Baglanti bag = new Baglanti();
		//Time siparis = new Time(19, 00, 01);
		//Time siparis = new Time(07, 59, 59);
		//Time siparis = new Time(8,00,01);
		Time siparis = new Time(18,59,59);
		bag.bilgiGetir(siparis);
		System.out.println("Sipariş saati: " + siparis);
	}

}
