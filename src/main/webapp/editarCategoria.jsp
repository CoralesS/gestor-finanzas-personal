<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${empty categoria ? 'Nueva Categoría' : 'Editar Categoría'}</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-5">

            <!-- Tarjeta principal -->
            <div class="card shadow-sm border-0 rounded-4 overflow-hidden">
                <div class="card-header bg-primary text-white text-center py-4 border-0">
                    <h4 class="mb-0 fw-bold">
                        <i class="bi ${empty categoria ? 'bi-plus-circle' : 'bi-pencil-square'} me-2"></i>
                        ${empty categoria ? 'Registrar Categoría' : 'Editar Categoría'}
                    </h4>
                </div>

                <div class="card-body p-4 p-md-5">
                    <form action="CategoriaController?accion=${empty categoria ? 'crear' : 'actualizar'}" method="POST">

                        <!-- ID Oculto para uso lógico -->
                        <input type="hidden" name="id" value="${categoria.id}">

                        <!-- Campo Nombre -->
                        <div class="mb-4">
                            <label for="nombre" class="form-label fw-bold text-secondary small">NOMBRE DE LA CATEGORÍA</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white text-primary border-end-0"><i class="bi bi-tag-fill"></i></span>
                                <input type="text" class="form-control border-start-0 py-2 px-3" id="nombre" name="nombre"
                                       value="${categoria.nombre}" placeholder="Ej. Alimentación" required autofocus>
                            </div>
                        </div>

                        <!-- Campo Descripción -->
                        <div class="mb-5">
                            <label for="descripcion" class="form-label fw-bold text-secondary small">DESCRIPCIÓN</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white text-primary border-end-0 align-items-start pt-3"><i class="bi bi-card-text"></i></span>
                                <textarea class="form-control border-start-0 py-2 px-3" id="descripcion" name="descripcion" rows="3"
                                          placeholder="Detalles sobre esta categoría...">${categoria.descripcion}</textarea>
                            </div>
                        </div>

                        <!-- Botones de Acción -->
                        <div class="d-flex flex-column gap-3">

                            <button type="submit" class="btn btn-primary btn-lg fw-bold shadow-sm w-100">
                                <i class="bi ${empty categoria ? 'bi-cloud-arrow-up-fill' : 'bi-save-fill'} me-2"></i>
                                ${empty categoria ? 'Guardar Categoría' : 'Actualizar Cambios'}
                            </button>

                            <a href="CategoriaController?accion=listar" class="btn btn-light border text-secondary fw-bold w-100 py-2">
                                <i class="bi bi-arrow-left-circle me-2"></i>Cancelar y Volver
                            </a>
                        </div>

                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>