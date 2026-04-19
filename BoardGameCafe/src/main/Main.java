package main;

import modelo.*;
import sujetos.*;
import articulos.*;
import persistencia.*;

import java.util.*;
import java.time.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestorPersistencia gp = new GestorPersistencia();
        Café cafe;

        // 🔹 CARGAR / CREAR
        try {
            cafe = gp.cargarCafe();
            System.out.println("Café cargado");
        } catch (Exception e) {
            cafe = new Café(
                50,
                new ArrayList<>(),   // menu
                new ArrayList<>(),   // mesas
                new ArrayList<>(),   // historialPrestamos
                new ArrayList<>(),   // empleados
                new ArrayList<>(),   // inventarioVenta
                new ArrayList<>(),   // inventarioPrestamo
                new HashMap<>(),     // historialComprasUsuario
                new ArrayList<>(),   // reservas
                new ArrayList<>(),   // solicitudes
                new ArrayList<>(),   // ventas
                new ArrayList<>(),   // venta
                0,0
            );
            System.out.println("Café nuevo creado");
        }
        ArrayList<UsuarioComprador> usuarios = cafe.getUsuarios();

        if (cafe.getEmpleados().isEmpty()) {
            ArrayList<DayOfWeek> dias = new ArrayList<>();
            dias.add(DayOfWeek.MONDAY);

            Turno turno = new Turno(
                dias,
                LocalTime.of(0, 0),
                LocalTime.of(23, 59),
                null
            );

            Mesero m1 = new Mesero("Mesero1", 25, 101,
                    new ArrayList<>(), 1234, "m1",
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    0, turno, new ArrayList<>());

            Mesero m2 = new Mesero("Mesero2", 26, 102,
                    new ArrayList<>(), 1234, "m2",
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    0, turno, new ArrayList<>());

            Cocinero c1 = new Cocinero("Cocinero1", 30, 103,
                    new ArrayList<>(), 1234, "c1",
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    0, turno);

            cafe.getEmpleados().add(m1);
            cafe.getEmpleados().add(m2);
            cafe.getEmpleados().add(c1);

            System.out.println("Empleados creados");
        }

        if (cafe.getMesas().isEmpty()) {
            cafe.getMesas().add(new Mesa(false, 1, new ArrayList<>(), false, 2));
            cafe.getMesas().add(new Mesa(false, 2, new ArrayList<>(), false, 4));
            cafe.getMesas().add(new Mesa(false, 3, new ArrayList<>(), false, 6));
            cafe.getMesas().add(new Mesa(false, 4, new ArrayList<>(), false, 8));
            System.out.println("Mesas creadas");
        }

        if (cafe.getMenú().isEmpty()) {
            cafe.getMenú().add(new Bebida(8000,"Cafe",new ArrayList<>(),null,false,true));
            cafe.getMenú().add(new Bebida(7000,"Te",new ArrayList<>(),null,false,true));
            cafe.getMenú().add(new Bebida(6000,"Jugo",new ArrayList<>(),null,false,false));
            cafe.getMenú().add(new Bebida(12000,"Cerveza",new ArrayList<>(),null,true,false));
            cafe.getMenú().add(new Bebida(18000,"Vino",new ArrayList<>(),null,true,false));
            cafe.getMenú().add(new Pasteleria(10000,"Brownie",new ArrayList<>(),null));
            cafe.getMenú().add(new Pasteleria(9000,"Torta",new ArrayList<>(),null));
            System.out.println("Menú cargado");
        }

        if (cafe.getInventarioJuegosPrestamo().isEmpty()) {

            Juego j1 = new JuegoSimple("UNO", 2000, "Mattel", 4, false,
                    RestriccionEdad.TODAS_EDADES, TiposJuegos.CARTAS, 4);

            Juego j2 = new JuegoSimple("Catan", 1995, "Kosmos", 4, true,
                    RestriccionEdad.APTO_MENORES, TiposJuegos.TABLERO, 4);

            Juego j3 = new JuegoSimple("Jenga", 1983, "Hasbro", 4, false,
                    RestriccionEdad.TODAS_EDADES, TiposJuegos.ACCION, 4);

            cafe.getInventarioJuegosPrestamo().add(new JuegoPrestamo(true,false,EstadoJuego.NUEVO,j1,0));
            cafe.getInventarioJuegosPrestamo().add(new JuegoPrestamo(true,false,EstadoJuego.NUEVO,j2,0));
            cafe.getInventarioJuegosPrestamo().add(new JuegoPrestamo(true,false,EstadoJuego.NUEVO,j3,0));

            cafe.getInventarioJuegosVenta().add(new JuegoVenta(20000, 3 ,j1));
            cafe.getInventarioJuegosVenta().add(new JuegoVenta(30000, 5 ,j2));
            cafe.getInventarioJuegosVenta().add(new JuegoVenta(50000, 2 ,j3));

            System.out.println("Juegos cargados");
        }

        Reserva ultimaReserva = null;

        boolean run = true;

        while (run) {

            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Ver usuarios");
            System.out.println("3. Crear reserva");
            System.out.println("4. Crear préstamo (usa mesa de la reserva)");
            System.out.println("5. Crear venta (usa mesa de la reserva)");
            System.out.println("6. Guardar");
            System.out.println("7. Ver Ventas");
            System.out.println("7. Salir");

            int op;
            try {
                op = sc.nextInt();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Entrada inválida");
                continue;
            }

            switch (op) {

                case 1:
                    System.out.println("Nombre:");
                    String nombre = sc.next();
                    System.out.println("Edad:");
                    int edad = sc.nextInt();
                    System.out.println("Cedula:");
                    int cedula = sc.nextInt();

                    Cliente nuevo = new Cliente(
                            nombre, edad, cedula,
                            new ArrayList<>(), 1234, nombre,
                            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0
                    );

                    usuarios.add(nuevo);
                    cafe.setUsuarios(usuarios);
                    System.out.println("Usuario creado");
                    break;

                case 2:
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios");
                    } else {
                        for (int i = 0; i < usuarios.size(); i++) {
                            System.out.println(i + ". " + usuarios.get(i).getNombre());
                        }
                    }
                    break;
                    
                case 3:
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios");
                        break;
                    }

                    Cliente cli = (Cliente) usuarios.get(0);

                    System.out.println("Número de personas:");
                    int num = sc.nextInt();

                    System.out.println("Fecha (usar 2026,5,19,12 para que funcione");
                    System.out.println("Año:");
                    int y = sc.nextInt();
                    System.out.println("Mes:");
                    int m = sc.nextInt();
                    System.out.println("Día:");
                    int d = sc.nextInt();

                    System.out.println("Hora (0-23):");
                    int h = sc.nextInt();

                    try {
                        LocalDate fecha = LocalDate.of(y, m, d);
                        LocalTime hora = LocalTime.of(h, 0);

                        ultimaReserva = cafe.crearReserva(fecha, cli, num, hora);

                        System.out.println("Reserva creada");
                        System.out.println("Mesa asignada: " + ultimaReserva.getMesaReserva().getIdMesa());

                    } catch (Exception e) {
                        System.out.println("Error reserva: " + e.getMessage());
                    }
                    break;

                case 4:
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios");
                        break;
                    }
                    if (ultimaReserva == null) {
                        System.out.println("Primero crea una reserva");
                        break;
                    }

                    UsuarioComprador user = usuarios.get(0);

                    System.out.println("Juegos:");
                    for (int i = 0; i < cafe.getInventarioJuegosPrestamo().size(); i++) {
                        JuegoPrestamo jp = cafe.getInventarioJuegosPrestamo().get(i);
                        System.out.println(i + ". " + jp.getInfoJuego().getNombre() +
                                " | Disponible: " + jp.isDisponible());
                    }

                    int idxJuego = sc.nextInt();
                    Juego juego = cafe.getInventarioJuegosPrestamo().get(idxJuego).getInfoJuego();

                    try {
                        while (user.getJuegosPrestados().size() < 2) {
                            user.getJuegosPrestados().add(null);
                        }

                        cafe.crearPrestamo(LocalDate.of(2026,5,19), LocalDate.of(2026,5,21), juego, user, 1, ultimaReserva.getMesaReserva());

                        System.out.println("Préstamo creado");

                    } catch (Exception e) {
                        System.out.println("Error préstamo: " + e.getMessage());
                    }
                    break;
                    
                case 5:
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios");
                        break;
                    }
                    if (ultimaReserva == null) {
                        System.out.println("Primero crea una reserva");
                        break;
                    }

                    UsuarioComprador u = usuarios.get(0);
                    ArrayList<Item> items = new ArrayList<>();

                    boolean seguir = true;

                    while (seguir) {
                        System.out.println("Menú:");
                        for (int i = 0; i < cafe.getMenú().size(); i++) {
                            System.out.println(i + ". " +
                                    cafe.getMenú().get(i).getNombrePlatillo());
                        }

                        int idx = sc.nextInt();

                        System.out.println("Cantidad:");
                        int cant = sc.nextInt();

                        items.add(new Item(cant, cafe.getMenú().get(idx)));

                        System.out.println("¿Agregar otro? 1=si 0=no");
                        seguir = sc.nextInt() == 1;
                    }

                    try {
                        cafe.crearVenta(items, u, LocalDate.of(2026,5,19), ultimaReserva.getMesaReserva(), false, false
                        );

                        System.out.println("Venta creada");

                    } catch (Exception e) {
                        System.out.println("Error venta: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        gp.guardarCafe(cafe);
                        System.out.println("Guardado");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case 7:
                    System.out.println("Ventas registradas:");

                    if (cafe.getHistorialVentas().isEmpty()) {
                        System.out.println("No hay ventas");
                    } else {
                        for (Venta v : cafe.getHistorialVentas()) {
                            System.out.println("Cliente: " + v.getComprador().getNombre());
                            System.out.println("Fecha: " + v.getFechaVenta());
                            System.out.println("Total: " + v.getTotal());
                            System.out.println("-------------------");
                        }
                    }
                    break;

                case 8:
                    try {
                        gp.guardarCafe(cafe);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    run = false;
                    break;
                
            }
        }

        sc.close();
    }
}