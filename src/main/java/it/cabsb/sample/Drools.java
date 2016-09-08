package it.cabsb.sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.io.ResourceFactory;

public class Drools {

	public static void main(String[] args) throws IOException {
		InputStream is = ResourceFactory.newFileResource("/home/caballero/Scrivania/test.xls").getInputStream();
		SpreadsheetCompiler sc = new SpreadsheetCompiler();
		StringBuffer drl = new StringBuffer(sc.compile(is, InputType.XLS));
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/caballero/Scrivania/test.drl"));
		out.write(drl.toString());
		out.close();
	}

}
