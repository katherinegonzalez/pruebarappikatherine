# README #

### Capas de la aplicación ###

* Capa de presentación o vistas: Esta capa hace referencia a las interfaces de la aplicación ubicados en la carpeta res/layout. Aquí se tiene las siguientes interfaces:

	* Interfaz principal (activity_main): Es la primera interfaz que se visualiza en la aplicación, en la cual el usuario podrá visualizar el listado de películas, escoger el tipo y hacer búsquedas, además desde aquí puede ingresar a ver el detalle de cada película haciendo click en cada item de la lista.

	* Interfaz de detalle de la película (activity_detail_movie): Esta interfaz contiene un slider compuesto de dos fragments en los cuales el usuario puede visualizar el detalle:
		-Fragment de Detalle 1 (fragment_detail_movie 1): Aquí el usuario puede visualizar la imagen de la película y el resumen.
		-Fragment de Detalle 2 (fragment_detail_movie 2): Aquí el usuario podrá visualizar algunos datos de la película como titulo original, lenguajes, fecha de lanzamiento, etc. Además desde aquí podrá dirigirse al ver el trailer de la película.
	* Interfaz de Video (activity_video): En esta interfaz el usuario podrá mirar el video del trailer de la película. 

* Capa de negocio: En esta capa se encuentra toda la lógica del negocio de la aplicación. Esta capa conecta a la capa de persistencia o datos con la capa de presentación o vistas. Hacen parte de la capa de negocio las clases que están ubicadas en el paquete activity y el paquete fragments. En el paquete activity se encuentran: 

	* MainActivity: Contiene la lógica referente a la interfaz activity_main. Dentro de ésta se realiza la petición a los endpoints que traen el listado de películas, además se almacenan estos datos en caché para que la aplicación pueda ser utilizada de manera offline. Estos procesos  son realizados con la ayuda de las clases: ConvertGson, ubicada en el paquete util (permite la serializacion y deserializacion de un objeto), NetValidation, ubicada en el paquete util (permite validar si hay conexión a internet), SaveInCache, ubicada en el paquete storage (permite el almacenamiento de los datos en el cache del dispositivo). 
		Además hace uso de los métodos de la clase MovieAdapter la cual permite manejar el listado de películas y los items que la componen, dentro de ésta se realiza la petición al endpoint que me trae el detalle de una determinanda película.

	* DetailMoviePagerActivity: Contiene la lógica referente a la interfaz activity_detail_movie.  Dentro de ésta se construye el slider de los fragments que muestran el detalle de una película, para esto hace uso de la clase PagerAdapter ubicada en el paquete adapter. Estos fragments (paquete fragments) son:
	  -DetailMovie1Fragment y DetailMovie2Fragment : Se encargan de fijar los datos de detalle de la pelicula previamente guardados de manera global en las interfaces fragment_detail_movie 1 y fragment_detail_movie 2.

    * VideoActivity: Contiene la lógica que permite mostrar el video de la película en la interfaz activity_video.

* Capa de datos o persistencia: En esta capa se encuentran las clases que están en el paquete model, las cuales permiten fijar y obtener los datos de la película, ya sea mapeando la respuesta desde los endpoints de la API o mapeando la información que se ha guardado en forma de json el dispositivo.

* Capa de red: Permite realizar la conexión con la API, aquí se encuentran la clase y la interfaz que están en el paquete res: ApiClient y ApiServiceClientInterface.

### Responsabilidad de cada clase ###

* MainActivity: contiene la lógica referente a la interfaz activity_main. Dentro de ésta se realiza la petición a los endpoint que traen el listado de películas, se valida la conexión a internet y se almacenan estos datos en caché para que la aplicación pueda ser utilizada de manera offline.
* DetailMoviePagerActivity: contiene la lógica referente a la interfaz activity_detail_movie.  Dentro de ésta se construye el slider de los fragments que muestran el detalle de una película.
* DetailMovie1Fragment: contiene la lógica referente a la interfaz fragment_detail_movie1, en la cual se muestran datos del detalle de la película.
* DetailMovie2Fragment: contiene la lógica referente a la interfaz fragment_detail_movie2, en la cual se muestran datos del detalle de la película.
* VideoActivity: contiene la lógica que permite mostrar el video de la pelicula en la interfaz activity_video.
* AdapterMovie: permite el manejo del listado de películas y los items que la componen.
* PagerAdapter: permite el manejo del listado de fragments que compondrán el slider.
* GeneralResponse, Genres, BelongsToCollection, Languages, Movie, MovieDetailResponse, ProductionCompanies, Video, VideoResponse: gestión de los datos de las películas (obtener y fijar).
* SaveInCache: escribir y guardar datos en la memoria caché del dispositivo.
* ConvertGson: serializar y deserializar un objeto.
* Dialogs: manejo de los dialogs de la aplicación.
* NetValidation: valida la conexión a internet.
* Util: declara variables para utilizarse de manera global.

### Preguntas ###

### 1. ¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito? ###

* Prinicipio de resposabilidad única: Es un principio de la programación orientada a objetos que busca que cada una de las clases tenga una única responsabilidad (encapsulada dentro de la misma clase), teniendo en cuenta que sea coherente y evitando o reduciendo el acoplamiento. 

* Propósito: Su propósito es permitir que la clase pueda ser modificada (si llegara a necesitarse) sin ser afectada, y que pueda ser reutilizada fácilmente, lo cual permite tener un código más limpio.

### 2. ¿Qué características tiene, según su opinión, un "buen" código o código limpio? ###

Un código limpio o un "buen código" debe estar bien estructurado, organizado en paquetes de tal manera que sea fácil de entender; los nombres de los paquetes, clases, métodos y variables deben ser entendibles permitiendo así una mejor comprensión al leer el código; debe poder ser reutilizado fácilmente, cumpliendo con el principio de responsabilidad y evitar las redundancias de código, siguiendo el principio DRY(Don't repeat yourself). Además es importante evitar métodos y clases muy largos.