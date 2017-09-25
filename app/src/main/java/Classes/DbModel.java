package Classes;

/**
 * Created by Hendra on 07/05/2017.
 */
public class DbModel{
    private String prm1, prm2, prm3, prm4, prm5;

    public DbModel(String par1, String par2, String par3, String par4){
        this.prm1 = par1;
        this.prm2 = par2;
        this.prm3 = par3;
        this.prm4 = par4;
    }
    public DbModel(){

    }

    public String getPrm2() {
        return prm2;
    }

    public String getPrm1() {
        return prm1;
    }

    public void setPrm2(String prm2) {
        this.prm2 = prm2;
    }

    public void setPrm1(String prm1) {
        this.prm1 = prm1;
    }

    public String getPrm3() {
        return prm3;
    }

    public void setPrm3(String prm3) {
        this.prm3 = prm3;
    }

    public String getPrm4() {
        return prm4;
    }

    public String getPrm5() {
        return prm5;
    }

    public void setPrm4(String prm4) {
        this.prm4 = prm4;
    }

    public void setPrm5(String prm5) {
        this.prm5 = prm5;
    }
}
