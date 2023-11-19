class Diceroll{
    public static int dice(){
        //We get a random double value (0 to 1) from the Math.random
        var randomValue = Math.random();
        //Here we convert the double value from the Math.random to an integer
        //and we also have to use Math.floor to round down 
        int dice = (int) Math.floor(randomValue*6)+1; 
        return dice;
    }
}