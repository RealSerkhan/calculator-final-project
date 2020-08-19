package com.example.calculator.algorithm;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

abstract public class MyAlgorithm {

    public String eval(String operation) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("Nashorn");
        return engine.eval(operation).toString();
    }


}
