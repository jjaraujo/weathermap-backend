## Para executar o projeto, siga os passos a seguir:


- O projeto faz persistência no MongoDB, por isso é necessário a sua instalação. A versão utilizada no projeto foi a 4
- Clone o projeto https://github.com/jjaraujo/weathermap-backend.git
- Importe no eclipse como um Maven Project
- Faça um update maven no projeto (Alt+F5). 
- Execute como um Java aplication

# O projeto possui um controlador que escuta os endpoints a seguir:
- /city/save: Metodo Post, RequestBody:String city, Response:City
	Recebe um json de city em formato de string, salva e retorna a cidade para usuário
- /city/delete: Metodo Post, RequestBody:long id , Response:boolean
	Recebe o id da mensage e retorna um boolean de sucesso da exclusão
- /city/listOne: Metodo Get, Request: long id, Response:Optional<City>
	Recebe o id da city e retorna caso encontrado
- /city/listAll: Metodo Get, Response:List<City>
	Retorna todas as cidades cadastradas pelo usuário
- /city/findCity: Metodo Get, Request:String text, Response:Forecast
	Recebe a pesquisa, verifica se o texto passado pelo  front está correto e qual o tipo de pesquisa (por nome e país, por geolocalização, por cep e país ou por id).
	Devido as várias possibilizades de conexões que podem ser criadas, a criação definida no padrão Builder. 
- /city/findWeatherByIdCity: Metodo Get, Request: long id, ForecastResponse
	O mapeamento dos objetos foram feitos de acordo com a resposta do openweathermap, exceto o ForecastResponse, que foi pensado na performance do sistema e na economia de dados do usuário, tanto de memória quanto de tráfego. Tendo em vista que para cada previsão serão usados somente 9 atributos, o ForecastResponse armazena somente eles e os retorna para o usuário. Nessa resposta, os graus já são convertidos de kelvin para celsius. 

