package it.cabsb.drools.function;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.drools.base.accumulators.AccumulateFunction;

public class RandomSelectAccumulateFunction implements AccumulateFunction {

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {}
	
	protected static class RandomSelectData implements Serializable, Externalizable {
		private static final long serialVersionUID = -2679630497267449033L;
		public List<Object> list = new ArrayList<Object>();
		public transient Random random = new Random(System.currentTimeMillis());
		
		public RandomSelectData() {}
		
		@Override
		public void writeExternal(ObjectOutput out) throws IOException {}
		
		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {}
	}
	
	@Override
	public Class<?> getResultType() {
		return null;
	}

	@Override
	public Serializable createContext() {
		return new RandomSelectData();
	}

	@Override
	public void init(Serializable context) throws Exception {
		RandomSelectData data = (RandomSelectData) context;
	    data.list.clear();
	}

	@Override
	public void accumulate(Serializable context, Object value) {
		RandomSelectData data = (RandomSelectData) context;
	    data.list.add(value);
	}

	@Override
	public void reverse(Serializable context, Object value) throws Exception {
		RandomSelectData data = (RandomSelectData) context;
	    data.list.remove(value);
	}

	@Override
	public Object getResult(Serializable context) throws Exception {
		RandomSelectData data = (RandomSelectData) context;
		if(!data.list.isEmpty()) {
			return data.list.get(data.random.nextInt(data.list.size()));
		} else {
			return null;
		}
	}

	@Override
	public boolean supportsReverse() {
		return true;
	}

}
