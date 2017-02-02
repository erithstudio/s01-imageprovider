package com.example.helloworld.tasks;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;
//import io.dropwizard.servlets.tasks.PostBodyTask;

import java.io.PrintWriter;

//public class EchoTask extends PostBodyTask {
public class EchoTask extends Task {
    public EchoTask() {
        super("echo");
    }

//    @Override
//    public void execute(ImmutableMultimap<String, String> parameters, String body, PrintWriter output) throws Exception {
//
//    }

    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        output.print("test");
        output.flush();
    }
}
