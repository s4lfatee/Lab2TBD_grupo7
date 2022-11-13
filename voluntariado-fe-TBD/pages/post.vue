<template>
    <v-card
        class="mx-auto"
        elevation="2"
        outlined
    >
        <v-card-title class="text-h5">
            Agregar una institución
        </v-card-title>
        <v-card-subtitle>
            Llena el formulario para agregar una institución
        </v-card-subtitle>
        <v-card-text>
            <v-text-field
                v-model="form.name"
                label="Nombre de la institución:"
                outlined
                :counter="25"
            />
        </v-card-text>
        <v-card-actions>
            <v-btn
                color="primary"
                depressed
                v-on:click="postInstitution()"
            >
                Agregar
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    data() {
        return {
            form: {
                name: ''
            }
        }
    },
    methods: {
        postInstitution() {
            this.$axios.post("http://localhost:8081/institutions", {"institution": this.form.name})
            .then(response => {
                console.log(this.form.name)
            })
            .catch(error => {
                console.log(error)
            })
        }
    }
}
</script>
