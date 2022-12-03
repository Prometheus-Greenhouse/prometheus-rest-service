package tik.prometheus.rest.decisiontree

class DecisionNode<T>(var name: String) {
    var children: Map<T, DecisionNode<Any>> = mapOf()
}