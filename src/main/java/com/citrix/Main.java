package com.citrix;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("----- printDemo -----");
        printDemo();
        System.out.println("----- invocableDemo -----");
        invocableDemo();
        System.out.println("----- interfaceDemo -----");
        interfaceDemo();
        System.out.println("----- streamDemo -----");
        streamDemo();
        System.out.println("----- bindingDemo -----");
        bindingDemo();
        System.out.println("----- objectsDemo -----");
        objectsDemo();
        System.out.println("----- debugDemo -----");
        debugDemo();
    }

    private static void printDemo() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval("print('hello world');");
    }

    private static void invocableDemo() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval("function strlen(obj) { return obj.length(); }");
        Invocable invocable = (Invocable) engine;
        System.out.println(invocable.invokeFunction("strlen", "hello"));
    }

    private static void interfaceDemo() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval("function strlen(obj) { return obj.length(); }");
        Invocable invocable = (Invocable) engine;
        MyFunction mf = invocable.getInterface(MyFunction.class);
        System.out.println(mf.strlen("hello"));
    }

    private static void objectsDemo() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval("function objStuff(obj) { print( obj.key ); return { num: 123 } }");
        Invocable invocable = (Invocable) engine;
        MyClass mc = new MyClass();
        mc.setKey("value");
        invocable.invokeFunction("objStuff", mc);
    }

    private static void streamDemo() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval(new FileReader("src/main/script/stream.js"));
    }

    private static void bindingDemo() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        Bindings bindings = engine.createBindings();
        bindings.put("name", "value");
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        engine.eval("function getGlobal() { return name; }");
        Invocable invocable = (Invocable) engine;
        System.out.println(invocable.invokeFunction("getGlobal"));
    }

    private static void debugDemo() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        engine.eval("load('src/main/script/stream.js')");
    }

    public interface MyFunction {
        int strlen(String str);
    }

    public static class MyClass {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

}
