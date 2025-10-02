# Prueba técnica: Articulos publicados HackerNews

Realizar un test técnico para demostrar las habilidades de desarrollo en Android

## Objetivo
Ver los artículos publicados en "Hacker News"

## Requerimientos funcionales

- Listado de artículos publicados en Hacker News
  - Al inicio, y cuando la vista de lista se baja para actualizar, la aplicación debe
    conectarse a esta API que muestra artículos publicados recientemente sobre
    Android in Hacker News:
    http://hn.algolia.com/api/v1/search_by_date?query=android 
  - Si la aplicación se usa sin conexión, debería mostrar los elementos
    descargados la última vez.
- Elimina artículos publicados en la app
  -  Deberá poder deslizar el dedo en una celda y eliminar una publicación
     individual de esta vista (ver maquetas). 
  - Una vez que se elimina una publicación, no debería volver a aparecer incluso
     si se actualizan los datos.
- Ver detalle de articulo publicado
  - Si el usuario toca una publicación, muestre una vista web (dentro de la
    aplicación) con el artículo vinculado

## Requerimientos no funcionales

- Buenas prácticas en el uso del lenguaje de programación
- Separación de problemas
- Controlar la UI a partir de un modelo
- Arquitectura adecuada (MVVM)
- Consumo de API
- Persistencia de datos

## Tecnologías utilizadas

- Kotlin - Lenguaje principal
- Arquitectura MVVM
- Separación de capas (Arquitectura Limpia) 
- Patrón de repositorio (Abstracción de datos)
- Casos de uso (Lógica de negocio)
- Inyección de dependencias (Hilt)
- Cliente http (okhttp y retrofit)
- Serialización (gson)
- Programación asincrona (corrutinas)
- Manejo de estado reactivo (StateFlow)

## Autor
- Jorge Madrigal Rodríguez