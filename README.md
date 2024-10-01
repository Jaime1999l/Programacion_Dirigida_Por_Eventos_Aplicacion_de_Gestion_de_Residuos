https://github.com/Jaime1999l/Programacion_Dirigida_Por_Eventos_Aplicacion_de_Gestion_de_Residuos.git

  - Jaime López
  - Mario
  - Juan Manuel
  - Nicolas
  - Alberto 

# Aplicación de Gestión de Residuos - Documentación

## Índice
1. [Descripción de la Aplicación](#descripción-de-la-aplicación)
2. [Funcionalidades](#funcionalidades)
3. [Casos de Uso](#casos-de-uso)
4. [Estructura de Clases](#estructura-de-clases)
5. [Explicación de Clases](#explicación-de-clases)
    - [CalendarActivity](#calendaractivity)
    - [MapActivity](#mapactivity)
    - [StatsActivity](#statsactivity)
    - [DatabaseResiduos](#databaseresiduos)
    - [Reminder](#reminder)
    - [Residuos](#residuos)
    - [ResiduosDao](#residuosdao)
    - [GraficoBarras](#graficobarras)
    - [GraficoCircular](#graficocircular)
    - [PantallaPrincipal](#pantallaprincipal)
6. [Estructura de la Base de Datos](#estructura-de-la-base-de-datos)

---

## Descripción de la Aplicación

La **Aplicación de Gestión de Residuos** permite a los usuarios gestionar, registrar y visualizar la cantidad de residuos depositados según el tipo de contenedor y fecha. Además, ofrece gráficos visuales de estadísticas y la posibilidad de agregar puntos de reciclaje en un mapa interactivo.

La aplicación incluye las siguientes características principales:
- Gestión de recordatorios y residuos por fecha mediante un calendario.
- Visualización de puntos de reciclaje en un mapa.
- Gráficos de estadísticas de residuos recolectados.
  
---

## Funcionalidades

1. **Registro de residuos**: Los usuarios pueden registrar la cantidad de bolsas de residuos depositadas por fecha y tipo de contenedor.
2. **Gestión de recordatorios**: El usuario puede agregar recordatorios para fechas específicas.
3. **Visualización de residuos por fecha**: En el calendario, se muestran los residuos depositados en una fecha seleccionada.
4. **Mapa interactivo de puntos de reciclaje**: Los usuarios pueden agregar y visualizar puntos de reciclaje en un mapa.
5. **Visualización de estadísticas**: Gráficos circulares y de barras que muestran estadísticas de residuos por contenedor y por fecha.
6. **Menú de navegación**: Permite cambiar entre las diferentes pantallas de la aplicación, como el calendario, el mapa y las estadísticas.

---

## Casos de Uso

1. **Caso de uso 1 - Registro de residuos**: El usuario accede a la pantalla de estadísticas, introduce la fecha, el tipo de contenedor y la cantidad de bolsas, y luego presiona el botón de agregar.
2. **Caso de uso 2 - Consultar residuos por fecha**: El usuario selecciona una fecha en el calendario y se muestra la cantidad de residuos depositados en esa fecha.
3. **Caso de uso 3 - Visualizar puntos de reciclaje en el mapa**: El usuario puede ver y agregar puntos de reciclaje interactuando con el mapa.
4. **Caso de uso 4 - Consultar estadísticas**: El usuario selecciona un tramo temporal y/o una fecha específica para visualizar gráficos que muestran las cantidades de residuos depositados por contenedor o por día.
5. **Caso de uso 5 - Visualizar recordatorios.

---

## Estructura de Clases

com.example.programacion_dirigida_por_eventos_aplicacion_de_gestion_de_residuos
│
├── activity
│   ├── CalendarActivity.java
│   ├── MapActivity.java
│   └── StatsActivity.java
│
├── database
│   └── DatabaseResiduos.java
│
├── recordatorios
│   ├── Reminder.java
│   └── ReminderDao.java
│
├── residuos
│   ├── Residuos.java
│   ├── ResiduosDao.java
│   └── estadisticas
│       └── Estadistica.java
│
├── view
│   ├── GraficoBarras.java
│   └── GraficoCircular.java
│
└── PantallaPrincipal.java

## Explicación de Clases

### CalendarActivity
La clase `CalendarActivity` es responsable de mostrar el calendario y permitir al usuario interactuar con las fechas seleccionadas para agregar recordatorios o ver los residuos depositados en una fecha concreta.

- **Componentes principales**: 
  - `CalendarView`, `TextView` para mostrar la recolección y residuos, `EditText` para escribir recordatorios y residuos, y un botón para agregar recordatorios.
  
- **Funciones principales**:
  - Actualiza la vista de texto al seleccionar una fecha en el calendario.
  - Guarda y muestra recordatorios y residuos depositados en la base de datos para una fecha específica.
  - Usa `AsyncTask` para gestionar las consultas a la base de datos sin bloquear la interfaz de usuario.

### MapActivity
`MapActivity` permite al usuario interactuar con un mapa en el que se pueden agregar puntos de reciclaje.

- **Componentes principales**:
  - `ImageView` para mostrar la imagen del mapa, `FrameLayout` como contenedor del mapa, botones para centrar la imagen y agregar puntos de reciclaje.

- **Funciones principales**:
  - Gestiona el zoom y el desplazamiento en el mapa usando eventos táctiles.
  - Permite al usuario agregar puntos de reciclaje interactivos en la imagen del mapa.
  - Mantiene los puntos de reciclaje actualizados mientras se interactúa con el mapa.

### StatsActivity
`StatsActivity` gestiona la interfaz para agregar residuos y mostrar gráficos estadísticos.

- **Componentes principales**:
  - Campos de texto para introducir fecha y cantidad de bolsas, `Spinner` para elegir el tipo de contenedor, botones para agregar residuos y mostrar gráficos.

- **Funciones principales**:
  - Registra la cantidad de residuos depositados por fecha y tipo de contenedor.
  - Muestra gráficos circulares y de barras para estadísticas de residuos.

### DatabaseResiduos
`DatabaseResiduos` es la base de datos Room que almacena información sobre residuos y recordatorios.

- **Componentes principales**: 
  - Define las entidades `Residuos` y `Reminder`, y sus respectivos DAOs (`ResiduosDao` y `ReminderDao`).

- **Funciones principales**:
  - Proporciona una instancia singleton de la base de datos.
  - Maneja la creación y migración de la base de datos.

### Reminder
La clase `Reminder` representa un recordatorio asociado a una fecha específica.

- **Componentes principales**:
  - `id` (clave primaria), `date` (fecha del recordatorio) y `description` (descripción del recordatorio).

- **Funciones principales**:
  - Almacena y gestiona la información de los recordatorios.

### Residuos
La clase `Residuos` almacena la información sobre los residuos depositados.

- **Componentes principales**:
  - `id`, `fecha`, `bolsas` (cantidad de bolsas) y `tipoContenedor`.

- **Funciones principales**:
  - Gestiona el almacenamiento y recuperación de los residuos.

### ResiduosDao
La interfaz `ResiduosDao` define las consultas SQL relacionadas con los residuos.

- **Funciones principales**:
  - Insertar nuevos registros de residuos.
  - Consultar residuos por fecha, contenedor y tramos de fechas.

### GraficoBarras
`GraficoBarras` es una vista personalizada que dibuja un gráfico de barras en función de los datos proporcionados.

- **Componentes principales**: 
  - Datos de las barras y sus colores.

- **Funciones principales**:
  - Dibuja las barras en proporción a los valores máximos proporcionados.

### GraficoCircular
`GraficoCircular` es una vista personalizada que dibuja un gráfico circular en función de los datos proporcionados.

- **Componentes principales**: 
  - Datos para los segmentos del gráfico y sus colores.

- **Funciones principales**:
  - Dibuja un gráfico circular representando las proporciones relativas.

### PantallaPrincipal
`PantallaPrincipal` es la clase de la pantalla principal que incluye un menú de navegación para acceder a las diferentes funcionalidades de la aplicación.

- **Componentes principales**: 
  - `DrawerLayout` para el menú lateral, botones y opciones de navegación.

- **Funciones principales**:
  - Permite al usuario navegar entre las pantallas de calendario, mapa y estadísticas.

## Estructura de la Base de Datos

La base de datos Room consta de dos tablas principales:

1. **Tabla `registro_residuos`**: 
    - `id` (int, primary key)
    - `fecha` (String)
    - `bolsas` (int)
    - `tipoContenedor` (String)

2. **Tabla `reminders`**:
    - `id` (int, primary key)
    - `date` (long, timestamp)
    - `description` (String)


