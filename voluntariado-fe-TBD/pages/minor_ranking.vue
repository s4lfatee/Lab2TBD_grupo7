<template>
    <div>
        <v-container>
            <v-row>
                <v-col cols="12">
                    <v-card>
                        <v-card-title class="justify-center">
                            <h1>Voluntarios con menor ranking por tarea</h1>
                        </v-card-title>
                    </v-card>
                    <b-container class="fluid">
                        <b-row>
                            <b-col>
                                <div>
                                    <label for="example-datepicker">Fecha de inicio </label>
                                    <b-form-datepicker id="example-datepicker" v-model="inicio" class="mb-2">
                                    </b-form-datepicker>
                                </div>
                            </b-col>
                            <b-col>
                                <div>
                                    <label for="example-datepicker">Fecha De fin</label>
                                    <b-form-datepicker id="example-datepicker" v-model="fin" class="mb-2">
                                    </b-form-datepicker>
                                </div>
                            </b-col>
                        </b-row>
                    </b-container>
                    <b-container>
                        <b-row>
                            <button class="btn btn-primary" @click="getEmergencias">Buscar emergencias en el periodo
                                ingresado</button>
                        </b-row>
                    </b-container>
                    <b-container>
                        <b-row>
                            <b-col>
                                <b-table bordered hover :items="emergencias" :fields="fields1" dark
                                    @row-clicked="getTareasByEmergencia"></b-table>
                            </b-col>
                            <b-col>
                                <b-table bordered hover :items="tareas" :fields="fields2" dark>
                                </b-table>
                            </b-col>
                        </b-row>
                    </b-container>
                </v-col>
            </v-row>
        </v-container>
    </div>

</template>

<script>
export default {
    data() {
        return {
            inicio: '',
            fin: '',
            fields1: [{ key: 'id', label: 'Id' }, { key: 'nombre', label: 'Emergencia' }],
            tareas: [],
            fields2: [{ key: 'id', label: 'Id' }, { key: 'nombre', label: 'Tarea' }, { key: 'cant_vol_inscritos', label: 'Voluntarios inscritos' }, { key: 'descrip', label: 'Menor ranking' }],
            emergencias: [],
            id_emergencia: ''
        }
    },
    methods: {
        getEmergencias() {
            this.$axios.get("http://localhost:8081/emergencia", {
                params: {
                    inicio: this.inicio,
                    fin: this.fin
                }
            }).then(response => {
                this.emergencias = response.data;
            })
                .catch(error => {
                    console.log(error);
                });
        },
        getTasksByIdVolunteer(id_tarea, i) {
            let ranking = '';
            this.$axios.get("http://localhost:8081/voluntario/min/" + id_tarea)
                .then(response => {
                    ranking = response.data[0].nombre;
                    this.tareas[i].descrip = ranking;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getTareasByEmergencia(object) {
            let id = object.id;
            this.$axios.get("http://localhost:8081/tarea", {
                params: {
                    emergencia: id
                }
            }).then(response => {
                this.tareas = response.data;
                for (let i = 0; i < this.tareas.length; i++) {
                    this.tareas[i].descrip = '';
                }

                for (let i = 0; i < this.tareas.length; i++) {
                    this.getTasksByIdVolunteer(this.tareas[i].id, i);
                }
                console.log(this.tareas);
            })
                .catch(error => {
                    console.log(error);
                });
        },
        print() {
            console.log(this.inicio)
            console.log(this.fin)
        }
    },
}
</script>