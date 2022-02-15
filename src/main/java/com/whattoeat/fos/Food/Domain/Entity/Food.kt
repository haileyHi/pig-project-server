package com.whattoeat.fos.Food.Domain.Entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
    name = "food", indexes = [
        Index(name = "fk_category_id_idx", columnList = "category_id")
    ]
)
@DynamicUpdate
@DynamicInsert
class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category? = null

    @Column(name = "name", nullable = false, length = 45)
    var name: String = ""

    @Column(name = "image", nullable = true, length = 100)
    var image: String? = null

    @Column(name = "detail", nullable = true, length = 100)
    var detail: String? = null

    @Column(name = "is_approved")
    var isApproved: Int? = null
}