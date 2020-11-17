package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import organizacion.Organizacion;

import java.io.IOException;

public enum IsEqualsHelper implements Helper<String>{
	isEquals{
		public Object apply(String tipo, Options options) throws IOException {
			Organizacion o = options.param(0);

			if(tipo.equals(o.getTipo())){
				return  options.fn(o);
			}
			else{
				return options.inverse(this);
			}

		}
	},

}
