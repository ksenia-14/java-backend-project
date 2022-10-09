/**
 * Products.java
 * В данном файле реализован класс продуктов магазина.
 */

package attestation;

public class Products {
    private TypeProduct typeProduct;
    private int numberProduct;
    private String descriptionProduct;

    public Products(TypeProduct typeProduct, int numberProduct) {
        this.typeProduct = typeProduct;
        this.numberProduct = numberProduct;

        switch (typeProduct) {
            case FOOD ->        this.descriptionProduct = "Еда";
            case COSMETICS ->   this.descriptionProduct = "Косметика";
            case CLOTHES ->     this.descriptionProduct = "Одежда";
            case SHOES ->       this.descriptionProduct = "Обувь";
        }
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }
    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }
    public int getNumberProduct() {
        return numberProduct;
    }
    public void setNumberProduct(int numberProduct) {
        this.numberProduct = numberProduct;
    }
    public String getDescriptionProduct() {
        return descriptionProduct;
    }
    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }
}
