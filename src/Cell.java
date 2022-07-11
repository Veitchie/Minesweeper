public class Cell {
    private boolean hidden;
    private boolean bomb;
    private int value;

    public Cell(){
        this.bomb = false;
        this.hidden = true;
        this.value = 0;
    }

    public void setHidden(){
        this.hidden = false;
    }

    public boolean getHidden(){
        return hidden;
    }

    public boolean getBomb(){
        return bomb;
    }

    public void setBomb(){
        this.bomb = true;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getRawValue(){
        return value;
    }

    public String getValue() {
        if (hidden) {
            return "?";
        } else {
            if (bomb) {
                return "*";
            } else {
                if (value > 0) {
                    return String.valueOf(value);
                }else{
                    return " ";
                }
            }
        }
    }

}
