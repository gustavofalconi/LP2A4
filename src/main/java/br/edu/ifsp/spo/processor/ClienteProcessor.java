package br.edu.ifsp.spo.processor;

import java.util.ArrayList;
import java.util.List;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import br.edu.ifsp.spo.cliente.Cliente;
import br.edu.ifsp.spo.cliente.ClienteRepository;

public class ClienteProcessor {
	private static final String DEFAULT_NATIONALITY = "BR";
	private static final String MALE_GENDER = "M";
	private static final String FEMALE_GENDER = "F";

	private final PhoneNumberUtil phoneNumberUtil;

	public ClienteProcessor(ClienteRepository repository) {
		this.phoneNumberUtil = PhoneNumberUtil.getInstance();
	}

	public List<Cliente> processUsers(List<Cliente> users) {
		List<Cliente> processedUsers = users;

		for (Cliente user : processedUsers) {
			processUser(user);
		}

		return processedUsers;
	}

	private void processUser(Cliente user) {
		// Transformar contatos telefônicos no formato E.164
		try {
			user.setTelephoneNumbers(transformToE164(user.getTelephoneNumbers()));
			user.setMobileNumbers(transformToE164(user.getMobileNumbers()));
		} catch (NumberParseException e) {
			// Lidar com exceção de formato inválido de número de telefone
		}

		// Inserir a nacionalidade padrão
		user.setNationality(DEFAULT_NATIONALITY);

		// Alterar o valor do campo gender para F ou M
		if (user.getGender().equalsIgnoreCase("female")) {
			user.setGender(FEMALE_GENDER);
		} else if (user.getGender().equalsIgnoreCase("male")) {
			user.setGender(MALE_GENDER);
		}

		// Retirar o campo age de dob e registered
		user.getDob().setAge(null);
		user.getRegistered().setAge(null);

		// Adicionar a região ao objeto location
		String region = classifyRegion(user.getLocation().getState());
		user.getLocation().setRegion(region);

		// Adicionar a classificação
		String classificacao = classify(user.getLocation().getCoordinates().getLatitude(),
				user.getLocation().getCoordinates().getLongitude());
		user.setClassificacao(classificacao);
	}

	private List<String> transformToE164(List<String> phoneNumbers) throws NumberParseException {
		List<String> transformedNumbers = new ArrayList<>();

		for (String phoneNumber : phoneNumbers) {
			if (phoneNumber != null && !phoneNumber.isEmpty()) {
				transformedNumbers.add("+" + phoneNumberUtil.parse(phoneNumber, DEFAULT_NATIONALITY).getCountryCode()
						+ "" + phoneNumberUtil.parse(phoneNumber, DEFAULT_NATIONALITY).getNationalNumber());
			} else {
				transformedNumbers.add(phoneNumber);
			}
		}

		return transformedNumbers;
	}

	private String classifyRegion(String state) {

		if (state.equals("amazonas") || state.equals("pará") || state.equals("roraima") || state.equals("amapá")
				|| state.equals("rondônia") || state.equals("acre") || state.equals("tocantins")) {
			return "norte";
		} else if (state.equals("piauí") || state.equals("maranhão") || state.equals("pernambuco")
				|| state.equals("rio grande do norte") || state.equals("paraíba") || state.equals("ceará")
				|| state.equals("bahia") || state.equals("alagoas") || state.equals("sergipe")) {
			return "nordeste";
		} else if (state.equals("mato grosso") || state.equals("mato grosso do sul") || state.equals("goiás")
				|| state.equals("distrito federal")) {
			return "centro-oeste";
		} else if (state.equals("são paulo") || state.equals("rio de janeiro") || state.equals("espírito santo")
				|| state.equals("minas gerais")) {
			return "sudeste";
		} else if (state.equals("rio grande do sul") || state.equals("paraná") || state.equals("santa catarina")) {
			return "sul";
		} else {
			return "sem estado";
		}
	}

	private String classify(double latitude, double longitude) {
		
		// Limites para a categoria "ESPECIAL"
		double minlat_ESPECIAL = -46.361899;
        double maxlat_ESPECIAL = -34.276938;
        double minlon_ESPECIAL = -15.411580;
        double maxlon_ESPECIAL = -2.196998;

        // Limites para a categoria "NORMAL"
        double minlat_NORMAL = -54.777426;
        double maxlat_NORMAL = -46.603598;
        double minlon_NORMAL = -34.016466;
        double maxlon_NORMAL = -26.155681;

	    double minlat_ESPECIAL2 = -52.997614;
	    double maxlat_ESPECIAL2 = -44.428305;
	    double minlon_ESPECIAL2 = -23.966413;
	    double maxlon_ESPECIAL2 = -19.766959;

		// Classificação
		String classificacao = null;

		if ((latitude >= minlat_ESPECIAL && latitude <= maxlat_ESPECIAL) &&
                (longitude >= minlon_ESPECIAL && longitude <= maxlon_ESPECIAL)) {
			
            classificacao = "especial";
            
        } else if ((latitude >= minlat_ESPECIAL2 && latitude <= maxlat_ESPECIAL2) &&
                (longitude >= minlon_ESPECIAL2 && longitude <= maxlon_ESPECIAL2)) {
        	
        	classificacao = "especial";
        	
		}else if((latitude >= minlat_NORMAL && latitude <= maxlat_NORMAL) &&
                (longitude >= minlon_NORMAL && longitude <= maxlon_NORMAL)) {
			
            classificacao = "normal";
            
        } else {
            classificacao = "trabalhoso";
        }

		return classificacao;
	}

}