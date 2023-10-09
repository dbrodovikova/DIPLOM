package examples.calculator;

public class LoggableCalculator implements Calculable{

    protected Calculable calc;
    Loggable calcLogger = new CalcLogger();


    public LoggableCalculator(Calculable calc) {
        calcLogger.saveLog("" + calc.getResult());
        this.calc = calc;
    }


    @Override
    public Calculable sum(int arg) {
        calcLogger.saveLog(calc.getResult() + " + " + arg);
        return this.calc.sum(arg);
    }

    @Override
    public Calculable multi(int arg) {
        calcLogger.saveLog(calc.getResult() + " * " + arg);
        return this.calc.multi(arg);
    }

    @Override
    public int getResult() {
        int result = this.calc.getResult();
        calcLogger.saveLog(" = " + result);
        return this.calc.getResult();
    }
}
