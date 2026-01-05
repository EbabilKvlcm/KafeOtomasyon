package DataBase;

import Entities.OrderItem;
import Entities.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStore {

    // Masalar
    public static Map<Integer, Table> tableMap = new HashMap<>();

    // Masadaki aktif siparişler  ✅ YENİ
    public static Map<Integer, List<OrderItem>> activeOrders = new HashMap<>();

    // Kasaya gönderilenler
    public static Map<Integer, List<OrderItem>> kasaOrders = new HashMap<>();

    static {
        for (int i = 1; i <= 10; i++) {
            tableMap.put(i, new Table(i, "Masa " + i));
        }
    }
}
