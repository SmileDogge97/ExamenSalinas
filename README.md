# ExamenSalinas
Por cuestiones de tiempo en la aplicación móvil me faltó la visualización de videos en la interfaz de detalles y que la aplicación funcionara de forma offline.

Para resolver esto lo que podría hacer es guardar los datos de las películas en una base de datos interna usando la librería de Room, y la primera vez que se haga 
las diversas consultas al API, los datos del response se vayan almacenando dentro de la base de datos. Así en caso de que no haya internet la aplicación pueda consultar
a la base de datos.

Para la parte de los videos, podría usar la API de YouTube para android y por medio de esta buscar los trailers de la película, a la par que buscaría la forma de 
descargar los videos la primera vez que se consultan la lista de películas al API de The movie DB. De esta forma en caso de que no tenga acceso a internet, 
la aplicación puede solamente mostrar los videos descargados en la interfaz de detalle.
