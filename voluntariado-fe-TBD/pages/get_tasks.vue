<template>
    <v-simple-table>
        <template v-slot:default>
            <thead>
                <tr>
                    <th class="text-left">
                        Tareas asignadas
                    </th>
                    <th class="text-left">
                    </th>
                    <th class="text-left">
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="tarea in tareas" :key="tarea.id" v-bind:id="'tarea_'+tarea.id">
                    <td>{{ tarea.nombre }}</td>
                    <td>
                        <v-btn depressed color="primary" @click="aceptFunction(tarea.id)">
                            Aceptar
                        </v-btn>
                    </td>
                    <td>
                        <v-btn depressed color="error" @click="rejectFunction(tarea.id)">
                            Rechazar
                        </v-btn>
                    </td>
                </tr>
            </tbody>
        </template>
    </v-simple-table>
</template>

<script>
import axios from "axios";
export default {
    name: 'GetTasksByVolunteerPage',
    data: () => ({
        voluntario: '',
        tareas: [],
        id: ''
    }),
    methods: {
        aceptFunction(id) {
            axios.patch("http://localhost:8081/ranking?" + "id_voluntario=" + localStorage.getItem('id') + "&id_tarea=" + id + "&nuevo_estado=2")
                .then(response => {
                    console.log(response)
                })
        },
        rejectFunction(id) {
            axios.patch("http://localhost:8081/ranking?" + "id_voluntario=" + localStorage.getItem('id') + "&id_tarea=" + id + "&nuevo_estado=3")
                .then(response => {
                    console.log(response)
                })
        }
    },
    beforeCreate() {
        axios.get("http://localhost:8081/tarea/voluntario/" + localStorage.getItem('id'))
            .then(response => {
                this.tareas = response.data;
                console.log(localStorage.getItem('access_token'))
            })
    }
}
</script>
    
    









