#ifndef LIST_HPP
#define LIST_HPP

#include <memory>
#include <utility>

namespace matf {

template <typename T>
class list {
public:
    list() = default;

    ~list()
    {
        while (m_start)
        {
            pop_front();
        }
    }

    list(const list& other)
    {
        for (auto i = 0u; i < other.m_size; ++i)
        {
            push_back(other[i]);
        }
    }

    list(list&& other) noexcept
        : m_start(std::move(other.m_start))
        , m_size(other.m_size)
    {
        other.m_size = 0;
    }

    list& operator= (const list& other)
    {
        auto temp(other);
        std::swap(temp.m_start, m_start);
        std::swap(temp.m_size, m_size);
        return *this;
    }

    list& operator= (list&& other) noexcept
    {
        m_start = std::move(other.m_start);
        m_size = other.m_size;
        other.m_size = 0;
        return *this;
    }

    void push_front(const T value)
    {
        auto new_element = std::make_unique<element>(value, std::move(m_start));
        m_start = std::move(new_element);
        ++m_size;
    }

    void push_back(const T value)
    {
        auto new_element = std::make_unique<element>(value);

        if (!m_start)
        {
            m_start = std::move(new_element);
        }
        else
        {
            auto e = m_start.get();

            while (e->next) {
                e = e->next.get();
            }

            e->next = std::move(new_element);
        }

        ++m_size;
    }

    void pop_front()
    {
        auto old_start = std::move(m_start);
        m_start = std::move(old_start->next);
        m_size--;
    }

    size_t size() const
    {
        return m_size;
    }

    // Operator [] je promenjen da uvek vraca referencu,
    // da bismo izbegli kopiranje potencijalno velikih objekata.
    // Ocigledno, da bismo sprecili menjanje objekta,
    // potrebno je da vratimo konstantnu referencu.
    const T& operator[] (const size_t index) const
    {
        return at(index)->value;
    }
    T& operator[] (const size_t index)
    {
        return at(index)->value;
    }

private:
    struct element
    {
        explicit element(T value = 0, std::unique_ptr<element>&& next = nullptr)
            : value(value)
            , next(std::move(next))
        {}

        T value;
        std::unique_ptr<element> next;
    };

    element* at(const size_t index) const
    {
        if (!m_start)
        {
            return nullptr;
        }

        auto e = m_start.get();

        for (auto i = 0u; i < index; ++i)
        {
            e = e->next.get();

            if (!e)
            {
                return nullptr;
            }
        }

        return e;
    }

    std::unique_ptr<element> m_start = nullptr;
    size_t m_size = 0;
};

} // namespace matf

template<typename T>
std::ostream& operator<<(std::ostream& out, const matf::list<T> & lst)
{
    for (auto i = 0u; i < lst.size(); ++i)
    {
        out << lst[i] << " ";
    }
    return out;
}

#endif /* ifndef LIST_HPP */