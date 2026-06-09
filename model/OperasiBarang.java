package model;

import exception.ValidationException; 
import java.util.List;

// Interface Operasi CRUD untuk pengelolaan barang 
public interface OperasiBarang {
    void tambahBarang(Barang barang) throws ValidationException;
    List<Barang> getAllBarang();
    void updateBarang(String id, String nama, int jumlah, String lokasi) throws ValidationException;
    void hapusBarang(String id) throws ValidationException;
}