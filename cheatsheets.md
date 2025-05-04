# Reminders
- #DifferentNameMapping
- #UsesAnotherMapperToMapInsideClass
  - Aqui dizemos para o mapper de Section que já sabemos mapear o drink e por isso passamos o "uses" na anotação da classe.
- #AvoidCircularDependencyMapStruct
  - Aqui resolvemos o problema que 1 drink tem N sections e 1 section tem N drinks e o map struct tinha uma dependencia circular.
- @ManyToMany